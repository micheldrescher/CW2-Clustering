# CW2-Clustering

## About
This project implements a Principle Components Analysis (PCA) over the draft 13 cloud computing characteristics definied by NIST. The analysis is based on the responses of about 60 projects, or organisations, scoring the importance of each of these characteristics on a scale from 0 (not important at all) to 9 (critically important) for their work.

## Repository structure
The repository comprises three components (apart from README.md) as follows:

- <b>External projects</b><br>
  Provides the complete source code of external projects used in this application, including any modifications OeRC has implemented over the course of the integraiton.
- <b>Eclipse libraries</b><br>
  The libraries contained herein are required to successfully build and deploy the web app. Whether you configure your Eclipse environment to use these as libraries (as I have done it), or just take all the JAR files and lump them together in the classpath is your choice.
- <b>CW2ClusteringWebApp</b><br>
  This is the actual Eclipse project, with project configuration and all. It is <em>not</em> self-contained; it requires you to configure the eclipse libraries accordingly in the java build path.
