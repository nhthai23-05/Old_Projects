from PyQt6 import QtWidgets
from PyQt6.QtWidgets import QDialog, QLineEdit, QMainWindow, QWidget, QStackedLayout, QPushButton, QLabel, QVBoxLayout, QHBoxLayout, QGridLayout, QStatusBar, QMessageBox
from PyQt6.QtCore import QTimer
from PyQt6.QtGui import QImage, QPixmap, QIcon
import cv2
import sys
import Discrete.utils.Encoding as encoding
import Discrete.utils.Recognition as recognition
from datetime import datetime
import pandas as pd
import os
import face_recognition
import openpyxl
import pickle
class MainWindow(QMainWindow):
    register_cam = False
    status = "Take photo to recognize"
    stack_navigator = QStackedLayout()
    frame = None
    student_id = None
    cam = cv2.VideoCapture(0)
    count = 0
    original_frame = None

    def __init__(self):
        super().__init__()

        # Main window settings
        self.setWindowTitle("Attendance System")
        self.setGeometry(400, 100, 1000, 700)
        self.setStyleSheet("""
            QMainWindow {
                background-color: lightpink;
            }   
            QMenuBar {
                background-color: lightpink
            }
            QStatusBar {
                background-color: purple;
                color: lightpink;
            }
            QLabel {
                border: 2px solid purple;
            }
            QPushButton {
                background-color: lightpink;
                color: purple;
                border: 2px solid purple;
                border-radius: 5px;
            }
            QPushButton:hover {
                background-color: purple;
                color: lightpink;
            }
            QPushButton:pressed {
                background-color: violet;
                color: mangenta;
            }
            """)
        self.setWindowIcon(QIcon("tải xuống.png"))

        # Central widget
        self.central_widget = QWidget()
        self.setCentralWidget(self.central_widget)
        self.central_widget.setLayout(self.stack_navigator)

        # Pages
        self.camera_page = CameraPage()
        self.stack_navigator.addWidget(self.camera_page)
        self.attendance_page = AttendancePage()
        self.stack_navigator.addWidget(self.attendance_page)
        self.register_page = RegisterPage()
        self.stack_navigator.addWidget(self.register_page)
        self.register_page_2 = RegisterPage2()
        self.stack_navigator.addWidget(self.register_page_2)

        # Set first page
        self.stack_navigator.setCurrentIndex(0)


class CameraPage(QWidget):
    def __init__(self):
        super().__init__()
        if not MainWindow.cam.isOpened():
            print("Error: Camera is not opened")
            sys.exit()
        MainWindow.cam.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
        MainWindow.cam.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

        # Layout settings
        self.hbox = QHBoxLayout()
        self.setLayout(self.hbox)    

        self.cam_label = QLabel()
        self.cam_label.setFixedSize(640, 480)
        self.hbox.addWidget(self.cam_label)

        self.take_photo_btn = QPushButton("Take Photo")
        self.take_photo_btn.setFixedSize(150, 60)
        self.take_photo_btn.clicked.connect(self.take_photo)
        self.hbox.addWidget(self.take_photo_btn)

        # Timer for updating frame
        self.timer = QTimer()
        self.timer.timeout.connect(self.update_frame)
        self.timer.start(10)

    def take_photo(self):
        ret, original_frame = MainWindow.cam.read()
        cap = MainWindow.cam
        if MainWindow.register_cam:

            MainWindow.original_frame = cv2.cvtColor(original_frame.copy(), cv2.COLOR_BGR2RGB)
            face_loc = face_recognition.face_locations(original_frame)
            if len(face_loc) > 0:
                top, right, bottom, left = face_loc[0]
                top, right, bottom, left = top, right, bottom, left 
                cv2.rectangle(original_frame, (left, top), (right, bottom), (0, 255, 0), 2)
                MainWindow.frame = cv2.cvtColor(original_frame, cv2.COLOR_BGR2RGB) 
                MainWindow.stack_navigator.setCurrentIndex(3)
            else:
                QMessageBox.information(self, "No Face Detected", "No face detected. Please try again.")               


        else:   
            
            student_id, face_loc = recognition.recognizeFace(original_frame, cap)

            # Hiển thị thông tin nếu nhận diện được
            if student_id and face_loc:
                top, right, bottom, left = face_loc
                top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
                cv2.rectangle(original_frame, (left, top), (right, bottom), (0, 255, 0), 2)
                cv2.rectangle(original_frame, (left, bottom - 35), (right, bottom), (0, 255, 0), cv2.FILLED)
                cv2.putText(original_frame, student_id, (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 2)
                MainWindow.frame =  cv2.cvtColor(original_frame, cv2.COLOR_BGR2RGB)
                MainWindow.stack_navigator.setCurrentIndex(1)
                MainWindow.student_id = student_id

                
            elif face_loc and not student_id:
                
                top, right, bottom, left = face_loc
                top, right, bottom, left = top * 4, right * 4, bottom * 4, left * 4
                cv2.rectangle(original_frame, (left, top), (right, bottom), (0, 0, 255), 2)
                cv2.rectangle(original_frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
                cv2.putText(original_frame, 'unknown', (left + 6, bottom - 6), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 0), 2)
                MainWindow.frame =  cv2.cvtColor(original_frame, cv2.COLOR_BGR2RGB)
                MainWindow.stack_navigator.setCurrentIndex(2)
                # QMessageBox.information(self, "Unknown Student", "Student cannot be identified. Please take photo 3 times to register.")
            else:
                cv2.putText(original_frame, "No Face Detected", (160,40), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 255), 2)
                MainWindow.frame =  cv2.cvtColor(original_frame, cv2.COLOR_BGR2RGB)
                QMessageBox.information(self, "No Face Detected", "No face detected. Please try again.")
    def update_frame(self):
        ret, frame = MainWindow.cam.read()
        if ret:
            frame = cv2.flip(frame, 1)
            frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            h, w, ch = frame.shape
            qimg = QImage(frame.data, w, h, w * ch, QImage.Format.Format_RGB888)
            qpix = QPixmap.fromImage(qimg)
            self.cam_label.setPixmap(qpix)

class AttendancePage(QWidget):
    def __init__(self):
        super().__init__()
        self.vbox = QVBoxLayout()
        self.hbox = QHBoxLayout()
        self.setLayout(self.hbox)

        self.image_label = QLabel()
        self.image_label.setFixedSize(640, 480)
        self.hbox.addWidget(self.image_label)
        self.hbox.addLayout(self.vbox)

        self.mark_attendance_btn = QPushButton("Mark Attendance")
        self.mark_attendance_btn.setFixedSize(150, 60)
        self.mark_attendance_btn.clicked.connect(self.mark_attendance)
        self.vbox.addWidget(self.mark_attendance_btn)

        self.retake_photo_btn = QPushButton("Retake Photo")
        self.retake_photo_btn.setFixedSize(150, 60)
        self.retake_photo_btn.clicked.connect(self.retake_photo)
        self.vbox.addWidget(self.retake_photo_btn)

        # Timer for updating frame
        self.timer = QTimer()
        self.timer.timeout.connect(self.show_photo)
        self.timer.start(10)

    def show_photo(self):
        if MainWindow.frame is not None:
            qimg = QImage(MainWindow.frame.data, 640, 480, 640 * 3, QImage.Format.Format_RGB888)
            qpix = QPixmap.fromImage(qimg)
            self.image_label.setPixmap(qpix)
    def mark_attendance(self):
        student_id = MainWindow.student_id.strip()
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
    def retake_photo(self):
        MainWindow.stack_navigator.setCurrentIndex(0)
class RegisterPage(QWidget):
    def __init__(self):
        super().__init__()

        # Layout settings
        self.vbox = QVBoxLayout()
        self.hbox = QHBoxLayout()
        self.setLayout(self.hbox)

        self.image_label = QLabel()
        self.image_label.setFixedSize(640, 480)
        self.hbox.addWidget(self.image_label)
        self.hbox.addLayout(self.vbox)

        self.register_btn = QPushButton("Register")
        self.register_btn.setFixedSize(150, 60)
        self.register_btn.clicked.connect(self.register)
        self.vbox.addWidget(self.register_btn)

        self.retake_photo_btn = QPushButton("Retake Photo")
        self.retake_photo_btn.setFixedSize(150, 60)
        self.retake_photo_btn.clicked.connect(self.retake_photo)
        self.vbox.addWidget(self.retake_photo_btn)

        # Timer for updating frame
        self.timer = QTimer()
        self.timer.timeout.connect(self.show_photo)
        self.timer.start(10)

    def show_photo(self):
        if MainWindow.frame is not None:
            qimg = QImage(MainWindow.frame.data, 640, 480, 640 * 3, QImage.Format.Format_RGB888)
            qpix = QPixmap.fromImage(qimg)
            self.image_label.setPixmap(qpix)

    def register(self):
        dial = InputDialog()
        dial.exec()
        

    def retake_photo(self):
        MainWindow.stack_navigator.setCurrentIndex(0)

class RegisterPage2(QWidget):
    def __init__(self):
        super().__init__()
        self.vbox = QVBoxLayout()
        self.hbox = QHBoxLayout()
        self.setLayout(self.hbox)

        self.image_label = QLabel()
        self.image_label.setFixedSize(640, 480)
        self.hbox.addWidget(self.image_label)
        self.hbox.addLayout(self.vbox)

        self.register_btn = QPushButton("Submit")
        self.register_btn.setFixedSize(150, 60)
        self.register_btn.clicked.connect(self.take_photo)
        self.vbox.addWidget(self.register_btn)

        self.retake_photo_btn = QPushButton("Retake Photo")
        self.retake_photo_btn.setFixedSize(150, 60)
        self.retake_photo_btn.clicked.connect(self.retake_photo)
        self.vbox.addWidget(self.retake_photo_btn)

        # Timer for updating frame
        self.timer = QTimer()
        self.timer.timeout.connect(self.show_photo)
        self.timer.start(10)
    
    def take_photo(self):
        path = 'data'
        save_path = os.path.join(path, MainWindow.student_id)
        if not os.path.exists(save_path):
            os.makedirs(save_path)
        original_frame = cv2.cvtColor(MainWindow.original_frame, cv2.COLOR_RGB2BGR)
        img_name = f"{MainWindow.student_id}_{MainWindow.count + 1}.jpg"
        img_path = os.path.join(save_path, img_name)
        cv2.imwrite(img_path, original_frame)
        MainWindow.count += 1
        if MainWindow.count == 1:
            QMessageBox.information(self, "Register", "Look lelf.")

        if MainWindow.count == 2:
            QMessageBox.information(self, "Register", "Look right.")
        if MainWindow.count == 3:
            MainWindow.register_cam = False
            MainWindow.count = 0
            QMessageBox.information(self, "Register", "Encoding...")
            encodeListKnown = recognition.encodeListKnown
            classNames = recognition.classNames
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
                classNames.append(MainWindow.student_id)
            
            QMessageBox.information(self, "Register", "Encoding completed")
        MainWindow.stack_navigator.setCurrentIndex(0)

    def show_photo(self):
        if MainWindow.frame is not None:
            qimg = QImage(MainWindow.frame.data, 640, 480, 640 * 3, QImage.Format.Format_RGB888)
            qpix = QPixmap.fromImage(qimg)
            self.image_label.setPixmap(qpix)

    def retake_photo(self):
        MainWindow.stack_navigator.setCurrentIndex(0)

class InputDialog(QDialog):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Input Student ID")
        self.setGeometry(600, 300, 400, 200)

        self.vbox = QVBoxLayout()
        self.setLayout(self.vbox)

        self.student_id_input = QLineEdit()
        self.student_id_input.setPlaceholderText("Student ID")
        self.vbox.addWidget(self.student_id_input)

        self.register_btn = QPushButton("Submit")
        self.register_btn.clicked.connect(self.register)
        self.vbox.addWidget(self.register_btn)
 
    def register(self):
        if self.student_id_input.text().isdigit():
            MainWindow.student_id = self.student_id_input.text()
            MainWindow.register_cam = True
            MainWindow.stack_navigator.setCurrentIndex(0)
            QMessageBox.information(self, "Register", "Please take photo 3 times to register.")
            QMessageBox.information(self, "Register", "Look at the camera.")
            self.close()
    

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec())