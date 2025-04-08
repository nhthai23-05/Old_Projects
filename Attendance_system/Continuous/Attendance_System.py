import Continuous.utils.Encoding as encoding
import Continuous.utils.Recognition as recognition
import cv2
from collections import deque

# Bộ đệm khung hình để lưu kết quả nhận diện gần đây
recent_matches = deque(maxlen=50)  # Lưu kết quả 50 khung hình gần nhất

cap = cv2.VideoCapture(0)

while True:
    success, frame = cap.read()
    
    student_id, face_loc = recognition.recognizeFace(frame, cap)

    # Hiển thị thông tin nếu nhận diện được
    if student_id and face_loc:
        top, right, bottom, left = face_loc
        top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
        cv2.rectangle(frame, (left, bottom - 35), (right, bottom), (0, 255, 0), cv2.FILLED)
        cv2.putText(frame, student_id, (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 2)
        
        # Cập nhật kết quả nhận diện vào deque
        recent_matches.append(True)
    
    elif face_loc and not student_id:
        top, right, bottom, left = face_loc
        top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)
        cv2.rectangle(frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
        cv2.putText(frame, "unknown", (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 0), 2)
        
        # Lưu kết quả không nhận diện được vào deque
        recent_matches.append(False)
        
        # Nếu tất cả kết quả trong deque đều là False
        if len(recent_matches) == recent_matches.maxlen and all(not match for match in recent_matches):
            while True:
                print("Không nhận diện được sinh viên. Yêu cầu đăng ký?")
                print("1. Đăng ký sinh viên mới.")
                print("2. Bỏ qua.")
                action = input("Chọn hành động (1 hoặc 2): ").strip()

                if action == "1":
                    recognition.registerNewStudent(cap)
                    recent_matches.clear()  # Xóa deque sau khi đăng ký sinh viên mới
                    break
                elif action == "2":
                    print("Bỏ qua đăng ký.")
                    break
                else:
                    print("Lựa chọn không hợp lệ. Tiếp tục vòng lặp.")
    
    else:
        cv2.putText(frame, "No Face Detected", (160,40), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 255), 2)
        
    cv2.imshow('Attendance System', frame)

    # Nhấn ESC để thoát
    if cv2.waitKey(1) == 27:
        break

cap.release()
cv2.destroyAllWindows()