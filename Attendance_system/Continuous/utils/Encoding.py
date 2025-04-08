import os
import cv2
import face_recognition
import numpy as np
import pickle

# Đường dẫn tới thư mục ảnh
path = 'data'

# Kiểm tra và tạo thư mục nếu chưa tồn tại
if not os.path.exists(path):
    os.makedirs(path)
    print(f"Tạo thư mục dữ liệu tại: {path}")
    
encodeListKnown = []
classNames = []

# Tải và encode tất cả ảnh từ thư mục
def loadEncodings(path):
    
    if not os.listdir(path):  # Nếu thư mục trống
        print("Thư mục dữ liệu trống. Hệ thống sẽ yêu cầu đăng ký sinh viên mới khi phát hiện khuôn mặt.")
        return
    
    for folder in os.listdir(path):
        folderPath = os.path.join(path, folder)
        if os.path.isdir(folderPath):
            encodingsForStudent = []
            for imgName in os.listdir(folderPath):
                imgPath = os.path.join(folderPath, imgName)
                img = cv2.imread(imgPath)
                if img is not None:
                    rgbImg = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                    encode = face_recognition.face_encodings(rgbImg)[0]
                    encodingsForStudent.append(encode)
            if encodingsForStudent:
                encodeListKnown.append(encodingsForStudent)
                classNames.append(folder)

print("---------------------------")
print("Encoding...")
loadEncodings(path)
encodings = [encodeListKnown, classNames]
print("Encoding Complete")

with open("artifacts/EncodeFile.p", "wb") as file:
    pickle.dump(encodings, file)
print("File Saved")
print("---------------------------")