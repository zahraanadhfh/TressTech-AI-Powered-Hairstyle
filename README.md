# TressTech: AI Powered Hairstyle - Machine Learning Implementation

![This is an alt text.](https://i.pinimg.com/736x/64/b7/f4/64b7f49f3b0dc1c3fe84927a82a9bf65.jpg "This is a sample image.")

## TressTech: AI Powered Hairstyle Application
Tresstech is an innovative app that helps users, mainly males, choose the best hairstyle for their head shape and hair type. 
People often struggle to find a hairstyle that suits them, resulting in dissatisfaction with their haircut. Tresstech overcomes 
this issue by offering personalized styling recommendations. Users may upload a selfie or take a shot with the app's camera 
feature, and the machine learning system will analyze the image to identify their hair type and head shape. Based on this 
analysis, the app recommends hairstyles that are likely to suit the user's features, allowing them to get a look they are 
confident and satisfied with.

## Techonologies we used
* Machine Learning
1. NumPy
2. Scikit Learn
3. Tensorflow
4. TFlite
5. OpenCV
6. OS
7. Random
8. Pandas
9. K-Means Clustering
10. Matplotlib
## The Steps
1. Face detection using Haarcascade Classifier
2. Landmark extraction features for face shape
3. Drawing lines on forehead
4. Calculating angle between the landmarks that we calculated by using arcustangens.
5. Calculating similarity between the landmarks
6. Build model with CNN to classify the face shape based on the landmark data
