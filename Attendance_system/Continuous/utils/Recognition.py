import pickle
import face_recognition
import cv2
import numpy as np
import os
from datetime import datetime

path = 'data'

# Load the encoding file
print("Loading Encoded File...")
with open("artifacts/EncodeFile.p", "rb") as file:
    encodings = pickle.load(file)
    encodeListKnown, classNames = encodings
print("Encoded File Loaded")
print("---------------------------")

# Ghi danh (attendance) vào file CSV
def markAttendance(student_id):
    
    # Kiểm tra nếu file Attendance.csv chưa tồn tại
    if not os.path.exists('Attendance.csv'):
        with open('Attendance.csv', 'w') as f:
            f.write('Name,Time')  # Tiêu đề cột
            
    # Ghi dữ liệu điểm danh
    with open('Attendance.csv','r+') as f:
        myDataList = f.readlines()
        student_id_List = []
        for line in myDataList:
            entry = line.split(',')
            student_id_List.append(entry[0])
        if student_id not in student_id_List:
            now = datetime.now()
            dtString = now.strftime('%H:%M:%S')
            f.writelines(f'\n{student_id},{dtString}')

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
        
        # Nếu chưa có sinh viên nào trong database
        if not encodeListKnown or not classNames:
            print("Database trống. Yêu cầu đăng ký sinh viên mới.")
            registerNewStudent(cap)

        # So sánh với danh sách đã lưu
        for i, encodingsForStudent in enumerate(encodeListKnown):
            matches = face_recognition.compare_faces(encodingsForStudent, largest_face_enc, 0.43)
            distances = face_recognition.face_distance(encodingsForStudent, largest_face_enc)

            if any(matches):
                bestMatchIndex = np.argmin(distances)
                if matches[bestMatchIndex]:
                    student_id = classNames[i].upper()
                    markAttendance(student_id)
                    return student_id, largest_face_loc
        
        return None, largest_face_loc
    
    return None, None

# Chụp ảnh sinh viên mới và lưu vào model/data
def registerNewStudent(cap):
    student_id = input("Nhập mã số sinh viên mới: ").strip()
    save_path = os.path.join(path, student_id)

    # Tạo thư mục nếu chưa tồn tại
    if not os.path.exists(save_path):
        os.makedirs(save_path)

    print("Chụp ít nhất 3 ảnh cho sinh viên mới...")
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
        
        cv2.putText(frame, f"Press enter to capture image no.{count + 1}", (80, 40), 
                    cv2.FONT_HERSHEY_COMPLEX, 0.8, (0, 0, 255), 2)

        # Hiển thị khung hình
        cv2.imshow('Attendance System', frame)

        # Kiểm tra phím nhấn
        key = cv2.waitKey(1)  # 1ms delay để tiếp tục hiển thị khung hình
        if key == 13:  # Phím Enter
            img_name = f"{student_id}_{count + 1}.jpg"
            img_path = os.path.join(save_path, img_name)
            cv2.imwrite(img_path, original_frame)
            print(f"Đã lưu ảnh: {img_path}")
            count += 1

    # Cập nhật encoding mới
    print("Cập nhật danh sách mã hóa cho sinh viên mới...")
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
    print("Đăng ký thành công!")