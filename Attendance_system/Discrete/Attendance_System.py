import Discrete.utils.Encoding as encoding
import Discrete.utils.Recognition as recognition
import cv2
import pandas as pd
cap = cv2.VideoCapture(0)

while True:
    success, frame = cap.read()
    
    original_frame = frame.copy()
    cv2.putText(frame, "Press enter for attendance check", (100, 30), 
                cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 255), 2)
    cv2.imshow('Attendance System', frame)
    
    key = cv2.waitKey(1)
    if key == 13:  # Phím Enter để chụp khung hình
        
        student_id, face_loc = recognition.recognizeFace(original_frame, cap)

        # Hiển thị thông tin nếu nhận diện được
        if student_id and face_loc:
            top, right, bottom, left = face_loc
            top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
            cv2.rectangle(original_frame, (left, top), (right, bottom), (0, 255, 0), 2)
            cv2.rectangle(original_frame, (left, bottom - 35), (right, bottom), (0, 255, 0), cv2.FILLED)
            cv2.putText(original_frame, student_id, (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 2)
            print(f'Attendance marked successfully: {student_id}')
            cv2.imshow('Attendance System', original_frame)
            cv2.waitKey(2000)
            
        elif face_loc and not student_id:
            
            top, right, bottom, left = face_loc
            top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
            cv2.rectangle(original_frame, (left, top), (right, bottom), (0, 0, 255), 2)
            cv2.rectangle(original_frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
            cv2.putText(original_frame, 'unknown', (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 0), 2)
            print("Student cannot be identified.")
            
            cv2.imshow('Attendance System', original_frame)
            cv2.waitKey(1)
            
            while True:
                print("Please select an action:")
                print("1. Retake a photo (if surely already registered).")
                print("2. Register.")
                action = input("Please type in (1 or 2): ").strip()
                if action == '1':
                    print("Retake a photo...")
                    break  # Thoát khỏi vòng lặp, cho phép người dùng chụp ảnh lại
                elif action == '2':
                    print("Registering...")
                    recognition.registerNewStudent(cap)
                    break  # Thoát khỏi vòng lặp sau khi đăng ký
                else:
                    print("Invalid choice. Please try again.")
        else:
            cv2.putText(original_frame, "No Face Detected", (160,40), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 255), 2)
            cv2.imshow('Attendance System', original_frame)
            cv2.waitKey(2000)
        # Hiển thị khung hình kết quả trong 2 giây

    # Nhấn ESC để thoát
    elif key == 27:
        break

cap.release()
cv2.destroyAllWindows()