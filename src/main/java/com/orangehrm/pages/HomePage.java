# SELENIUM ORANGEHRM PROJECT – DAILY GIT COMMANDS

GitHub Repository:
https://github.com/Harman-cr7/Selenium-Test-Framework

Project Folder:
C:\Users\harma\eclipse-workspace\OrangeHRMProject

Branch: main

--------------------------------------------

STEP 1 – Open Git Bash and navigate to project

cd ~/eclipse-workspace/OrangeHRMProject

STEP 2 – Check changed files

git status

STEP 3 – Add all new and modified files

git add .

STEP 4 – Commit your changes

git commit -m "Describe your changes here"

Example:
git commit -m "Add login page automation"

STEP 5 – Upload changes to GitHub

git push

--------------------------------------------

IMPORTANT NOTES:

1. Always open Git Bash inside your OrangeHRMProject folder or use the cd command above.
2. Run git status before adding files.
3. Write a meaningful commit message.
4. Run git push after committing.
5. Do not run git init again.
6. Do not run git remote add origin again.
7. Do not run git push -u origin main again during normal daily work.

--------------------------------------------

YOUR DAILY WORKFLOW:

CODE → TEST → GIT STATUS → GIT ADD → GIT COMMIT → GIT PUSH

--------------------------------------------

IF YOU WANT TO CHECK YOUR LATEST COMMIT:

git log -1

IF YOU WANT TO CHECK YOUR GITHUB CONNECTION:

git remote -v

--------------------------------------------

REMEMBER:

Your local branch is MAIN.
Your GitHub remote is ORIGIN.
Your GitHub repository is Selenium-Test-Framework.