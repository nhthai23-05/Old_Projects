import cv2
import numpy as np
import face_recognition
import dlib

def detect(image):
    image = face_recognition.load_image_file(image)
    face_locations = face_recognition.face_locations(image, model='hog',number_of_times_to_upsample= 3)
    if face_locations:
        areas = [(bottom - top) * (right - left) for (top, right, bottom, left) in face_locations]
        top, right, bottom, left = face_locations[np.argmax(areas)]
        image = cv2.rectangle(image, (left , top ), (right ,  bottom), (0, 0, 255), 2) #0,0,255 is RGB, 2 is thickness
        cropped_image = image[top:bottom, left:right]
        cv2.resize(cropped_image, (640,640))
        cv2.imshow('image', cv2.cvtColor(image, cv2.COLOR_RGB2BGR))
        cv2.waitKey(0)
        cv2.destroyAllWindows()
        # return (left, top*0.5, right*0.9, bottom)
def draw_boxes(img_path, label_path):
    img = cv2.imread(img_path)
    h, w, _ = img.shape

    with open(label_path, 'r') as f:
        lines = f.readlines()

    for line in lines:
        x_center, y_center, width, height = map(float, line.strip().split()[1:])

        # Convert normalized coordinates to pixel coordinates
        x = int((x_center - width/2) * w)
        y = int((y_center - height/2) * h)
        x2 = int((x_center + width/2) * w)
        y2 = int((y_center + height/2) * h)

        # Draw the bounding box on the image
        cv2.rectangle(img, (x, y), (x2, y2), (0, 255, 0), 2)

    cv2.imshow('Image with Bounding Boxes', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    # return (x, y, x2, y2)

def calculate_iou(box1, box2):
    """Calculates the Intersection over Union (IoU) of two bounding boxes.

    Args:
        box1: A tuple of (x1, y1, x2, y2) representing the coordinates of the first bounding box.
        box2: A tuple of (x1, y1, x2, y2) representing the coordinates of the second bounding box.

    Returns:
        The IoU value.
    """

    x1, y1, x2, y2 = box1
    x1_, y1_, x2_, y2_ = box2

    # Find the intersection rectangle
    x_inter1 = max(x1, x1_)
    y_inter1 = max(y1, y1_)
    x_inter2 = min(x2, x2_)
    y_inter2 = min(y2, y2_)

    # Calculate the area of intersection
    width_inter = max(0, x_inter2 - x_inter1)
    height_inter = max(0, y_inter2 - y_inter1)
    area_inter = width_inter * height_inter

    # Calculate the area of each bounding box
    area_box1 = (x2 - x1) * (y2 - y1)
    area_box2 = (x2_ - x1_) * (y2_ - y1_)

    # Calculate the area of union

    # Calculate IoU
    iou = area_inter / area_box2 #box 2 is the ground truth

    return iou
# Replace with your image and label paths
img_path = '28_jpg.rf.7562d33f1f365fabf3db8226df66f579.jpg'
label_path = '28_jpg.rf.7562d33f1f365fabf3db8226df66f579.txt'
# groundtruth = draw_boxes(img_path, label_path)
# detection =  detect(img_path)
# iou = calculate_iou(detection, groundtruth)
# print(detection)  
# print(groundtruth)
# print(iou)
detect(img_path)