import pickle
import face_recognition
import cv2
import numpy as np
import os
from datetime import datetime
import pandas as pd
import openpyxl

path = 'data'

# Load the encoding file
print("Loading Encoded File...")
with open("artifacts/EncodeFile.p", "rb") as file:
    encodings = pickle.load(file)
    encodeListKnown, classNames = encodings
print("Encoded File Loaded")
print("---------------------------")

# Nhận diện khuôn mặt
def recognizeFace(frame, cap):
    
    small_frame = cv2.resize(frame, (0,0), None, 0.25, 0.25)
    rgb_frame = cv2.cvtColor(small_frame, cv2.COLOR_BGR2RGB)
    face_locs = face_recognition.face_locations(rgb_frame)

    if face_locs:
        # Chọn khuôn mặt lớn nhất
        areas = [(bottom - top) * (right - left) for (top, right, bottom, left) in face_locs]
        largest_face_loc = face_locs[np.argmax(areas)]
        largest_face_enc = face_recognition.face_encodings(rgb_frame, [largest_face_loc])[0]
        
        # # Nếu chưa có sinh viên nào trong database
        # if not encodeListKnown or not classNames:
        #     print("Database is empty. New student registration required.")
        #     registerNewStudent(cap)

        # So sánh với danh sách đã lưu
        for i, encodingsForStudent in enumerate(encodeListKnown):
            matches = face_recognition.compare_faces(encodingsForStudent, largest_face_enc, 0.43)
            distances = face_recognition.face_distance(encodingsForStudent, largest_face_enc)

            if any(matches):
                bestMatchIndex = np.argmin(distances)
                if matches[bestMatchIndex]:
                    student_id = classNames[i].upper()
                    return student_id, largest_face_loc
                
        return None, largest_face_loc
            
    return None, None

# Chụp ảnh sinh viên mới và lưu vào model/data
def registerNewStudent(cap):
    student_id = input("Student ID: ").strip()
    save_path = os.path.join(path, student_id)

    # Tạo thư mục nếu chưa tồn tại
    if not os.path.exists(save_path):
        os.makedirs(save_path)

    count = 0
    while count < 3:
        success, frame = cap.read()
        
        original_frame = frame.copy()
        
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        face_loc = face_recognition.face_locations(rgb_frame)
        
        # Nếu không phát hiện khuôn mặt
        if not face_loc:
            cv2.putText(frame, "No Face Detected", (160,40), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 255), 2)
            cv2.imshow('Attendance System', frame)
            cv2.waitKey(1)  # Hiển thị thông báo trong 2 giây
            continue  # Quay lại vòng lặp để chụp lại

        cv2.putText(frame, f"Press Enter to capture image no.{count + 1}", (80, 40), 
                    cv2.FONT_HERSHEY_COMPLEX, 0.8, (0, 0, 255), 2)

        # Hiển thị khung hình
        cv2.imshow('Attendance System', frame)

        # Kiểm tra phím nhấn
        key = cv2.waitKey(1)  # 1ms delay để tiếp tục hiển thị khung hình
        if key == 13:  # Phím Enter
            img_name = f"{student_id}_{count + 1}.jpg"
            img_path = os.path.join(save_path, img_name)
            cv2.imwrite(img_path, original_frame)
            print(f"Picture saved as: {img_path}")
            count += 1

    # Cập nhật encoding mới
    print("Update encodings...")
    newEncodings = []
    for imgName in os.listdir(save_path):
        imgPath = os.path.join(save_path, imgName)
        img = cv2.imread(imgPath)
        if img is not None:
            rgbImg = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            encode = face_recognition.face_encodings(rgbImg)[0]
            newEncodings.append(encode)
    if newEncodings:
        encodeListKnown.append(newEncodings)
        classNames.append(student_id)
    print("Registered succesfully!")
    
# Ghi danh (attendance) vào file excel
def markAttendance(student_id):
    day = datetime.now().strftime("%d-%m-%Y")
    
    # Kiểm tra nếu file Attendance.csv chưa tồn tại
    if not os.path.exists('Attendance.xlsx'):
        pd.DataFrame(columns=['ID', 'Time']).to_excel('Attendance.xlsx',sheet_name= day, index=False)

    if os.path.exists('Attendance.xlsx'):
        with pd.ExcelFile('Attendance.xlsx') as file:
            if day in file.sheet_names:
                data = pd.read_excel('Attendance.xlsx', sheet_name=day)
                data['ID'] = data['ID'].astype(str).str.strip()
                if student_id in data['ID'].values:
                    data.drop(data[data['ID'] == student_id].index, inplace = True)
                    print(f"Attempting to delete student with ID: {student_id}")
                new_entry = pd.DataFrame([[student_id, datetime.now().strftime("%H:%M:%S")]], columns=['ID', 'Time'])
                data = pd.concat([data, new_entry], ignore_index=True)
                with pd.ExcelWriter('Attendance.xlsx', mode='a', if_sheet_exists='replace') as writer:
                    data.to_excel(writer, sheet_name=day, index=False)
            else:
                new_entry = pd.DataFrame([[student_id, datetime.now().strftime("%H:%M:%S")]], columns=['ID', 'Time'])
                with pd.ExcelWriter('Attendance.xlsx', mode='a', if_sheet_exists='replace') as writer:
                    new_entry.to_excel(writer, sheet_name=day, index=False)