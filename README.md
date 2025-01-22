# Vocaliz Backend

Vocaliz optimizes your vocabulary learning experience with multiple features, including vocabulary list, categorization, search, ranking and quiz. 

## Key Features

- **Category:** Allow users to study vocabularies based on multiple purposes.

- **Vocabulary Overview:** Paginated vocabulary words list. Also available to filter the list by rank. On each vocabulary word card, include "See Answer", "Edit", "Delete" features for individual usage.

- **Rank-based design:** Rank refers to the familarity a user has with a vocab word. The app provides up to 3 levels of rank for users to pinpoint words with same familarity

- **Quiz:** Test user's ability to come up with vocabulary meanings in x seconds (customizable). Vocabulary words used in quiz would be selected from a curated vocabulary list.

- **Search:** Search a word's meaning.


## Tech Stack
- **Frontend:** Next.js, TypeScript, HTML, CSS
- **Backend:** Spring Boot, Java
- **Cloud:** Docker, AWS Elastic Bean, AWS S3, AWS CodePipeline


## Motivation
The idea started when I was preparing for GRE test, which requires a lot of vocabulary memorized. The popular vocabulary app at the time couldn't meet my study preference: Classifying words into multiple levels based on my familarity to them. This feature is crucial for those **who have different study plans for vocabularies tagged with different levels of familarity**. For example, I tend to put all my focus on those words that I always forgot first. After I feel more comfortable with them, I would then study those words that I am more familiar with. Therefore, I created this app that put emphasis on this feature.


## How to start

1. Go the the root of project folder and open __Terminal__.
2. Execute following commands:  
  - ```cd target```  
  - ```java -jar Vocaliz-0.0.1-SNAPSHOT.jar```
3. The server should run successfully.

## Documentation

- Checkout [This Notion Page](https://simple-lentil-d72.notion.site/Vocaliz-Backend-672a178b491942c7b78cda780f29ba48) to see detailed database schema and API formats.

- Checkout [Vocaliz Frontend](https://github.com/genewang0929/Vocaliz-Frontend) for individual demo of each feature.


## Contact / Connect
- **Chun-Han, Wang**
- genewang7@gmail.com
- [GitHub](https://github.com/genewang0929)
- [LinkedIn](https://www.linkedin.com/in/chun-han-wang/)
