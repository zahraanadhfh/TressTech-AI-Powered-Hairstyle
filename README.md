# TressTech: AI Powered Hairstyle - Cloud Computing

![This is an alt text.](https://i.pinimg.com/736x/64/b7/f4/64b7f49f3b0dc1c3fe84927a82a9bf65.jpg "This is a sample image.")

## TressTech: AI Powered Hairstyle Application
Tresstech is an innovative app that helps users, mainly males, choose the best hairstyle for their head shape and hair type. 
People often struggle to find a hairstyle that suits them, resulting in dissatisfaction with their haircut. Tresstech overcomes 
this issue by offering personalized styling recommendations. Users may upload a selfie or take a shot with the app's camera 
feature, and the machine learning system will analyze the image to identify their hair type and head shape. Based on this 
analysis, the app recommends hairstyles that are likely to suit the user's features, allowing them to get a look they are 
confident and satisfied with.

## Techonologies we used
* Cloud Computing
1. Express.js
2. Flask
3. Cloud SQL
4. Cloud Run
5. Cloud Storage

## Cloud Architecture 🏗️
In our application we use Cloud Run, Cloud Storage, and Cloud SQL services to support our application to run. So, the frontend will consume the API from two servers; Backend and Machine Learning models. For the Backend, it will directly connect to Cloud SQL as a database. For the Machine Learning models, it will directly connect to Cloud Storage to store the analyzed images from the user and to Cloud SQL as a database. We put these services in asia-southeast2 region.

![cloud architecture](https://github.com/chisiliaamanda/project_hairstyle/assets/133761119/944add2c-b7e3-4074-a873-386bed52ea77)

## The Steps 
1. Clone the GitHub repository in Cloud Shell and follow the steps below:
   ```
   git clone https://github.com/chisiliaamanda/project_hairstyle.git
   cd project_hairstyle
   ```
3. Deployment to Google Cloud using Cloud SQL, Cloud Storage, and Cloud Run
   - Set up Cloud SQL:
     * Create a Cloud SQL instance in Google Cloud Console.
     * Configure database and users.
     * Save connection details for use in the app.
   - Set Up Cloud Storage:
     * Create a bucket in Google Cloud Storage.
     * Configure the bucket to store the files the application needs.
   - Deployment with Cloud Run:
     * Dockerizing the Application
     * Build and Push Images
     * Deploy to Cloud Run
       
   Here is the code to deploy the backend into google cloud run:
   ```
   gcloud builds submit \
   --tag gcr.io/$GOOGLE_CLOUD_PROJECT/hair-project1234
   ```
   ```
   gcloud run deploy hair-project1234 \
   --image gcr.io/$GOOGLE_CLOUD_PROJECT/hair-project1234 \
   --platform managed \
   --region asia-southeast2 \
   --allow-unauthenticated \
   --max-instances=3 \
   --port=5000 \
   --service-account my-new-service-account@capstone-hairstyle.iam.gserviceaccount.com
   ```
