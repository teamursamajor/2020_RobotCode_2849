# 2020_RobotCode_2849
Welcome to Team Ursa Major's 2020 Programming GitHub Repository! This is where all of our code will be placed during the season.

*This README should now be fully updated for the 2020 season. Please contact Evan through GroupMe if anything is missing, needs clarification, or contains errors.*

#### Git Quick Reference for Team Members
We are using VS Code this year instead of Eclipse. This will be our second year using it, as it has become the official FIRST IDE. This guide will cover how to go from nothing to setting up the entire work environment.

#### Getting Started
**2020 FRC Game Tools:**

https://www.ni.com/en-us/support/downloads/drivers/download.frc-game-tools.html#333285

Follow the link above to install the file. Extract it, open the folder, and run the setup application. During setup, you may be asked to log in, so use teamursamajor@gmail.com and our regular password.

**VS Code and WPILib:**

https://github.com/wpilibsuite/allwpilib/releases

Follow the link above and install the WPILib installer that corresponds to your OS. Run the installer, then click "Select/Download VS Code." Click "Download." Once that’s complete, make sure all checkboxes are checked (unless you have already installed 2020 WPILib software on this machine and the software unchecked them automatically), then click "Execute Install." Don't forget to set Git Bash as the default shell and install the NavX libraries in VS Code (see those respective sections for more info).

**NavX-MXP:**

https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/

We use a sensor called the NavX MXP, which requires some outside libraries. Open the link and download the latest build of the libraries. After installing, run the setup program. Then, open VS Code and press CTRL + Shift + P to show all commands. Search for "Manage Vendor Libraries," then "Install new libraries (offline)," then click on the NavX libraries.

**JRE (Java Runtime Environment):**

If Strings or other basic objects are throwing errors, you are missing your JRE. Install it from https://java.com/en/download/manual.jsp

**Git Bash:**

This program allows you to utilize Git for your projects through a command line. You can install it at https://git-scm.com/downloads. Tutorials on how to use Git Bash are further below.

To use Git Bash in the VS Code terminal, open VS Code and open the command window by pressing CTRL + Shift + P. Search for "Select Default Shell" and select Git Bash.

#### Using Git Bash
While VS Code does have built-in integration with Git, we can also use Git from the command line or terminal (aka Git Bash). This allows for greater control and ease of access and is generally preferable. If you followed the above installation guides, then you should also have Git Bash accessible through the VS Code terminal, which is really nice.

#### Add the Repo to Your Computer
In the command line, type:

`git clone https://github.com/teamursamajor/2020_RobotCode_2849`

This clones the 2020 Robot Code repository to your computer, so Git will now track any changes you make to existing files.

#### Adding Files 
If you created a new file or changed a file, such as "Drive.java", you will need to tell Git to track this change. For example, simply type:

`git add Drive.java`

replacing Drive.java with the file you want to add, Git will now "stage" that change. You can also add multiple files in the same line.

Example: `git add Drive.java Lift.java Intake.java`

would add all three files.

#### Deleting Files 
If you want to remove a file from the repository, type:

`git rm Drive.java`

or whatever file you want to delete. **This deletes the file from your computer as well.**

#### Checking Files 
Typing:

`git status`

will tell you the status of your files, such as what has been added, modified, or deleted. Files under "Changes to be committed:" will be committed to the repository, while files under "Changes not staged for commit:" will need to be added before committing to make the changes permanent.

You can also check commit history by typing:

`git log`

and it will bring up a list of prior commits, commit messages, and the author/date. Press "q" to leave the list and return to the command line.

#### Committing

After editing a file (or adding a file) your code needs to be committed. Otherwise, the changes will only stay on your computer and not be added to the online repository. Typing:

`git commit`

will launch a text editor and allow you to add a message to your commit. The commit message should describe the changes you made, and if you leave it blank, it will not commit. If you want to skip opening the editor, typing:

`git commit -m [message]`

where [message] is your message will allow you to add a message without opening the editor. Write a useful message that explains the changes you made. Appropriate messages would look like: "This commit will fix errors in Robot.java, add Drive.java, and remove unnecessary code from Arm.java." You can also skip the "add" command with:

`git commit -a`

which will automatically add all files that have been changed. It will NOT add files that have been created. To add all content modified/created/removed use the command:

`git add -A`

(*Potentially dangerous; be aware when using it. You may end up adding user-specific files that we do not want on the repository.*)

Commit often, push when you want others to use your work, and always commit, pull, then push at the end of the day.

#### Pulling
When you start your work for the day, always be sure to pull immediately before doing anything. This adds changes made on other computers to your project. 

Try not to make any changes before pulling, as this will create a merge conflict, which can be stressful.

To pull, type:

`git pull`

and git will automatically add changes to your local project. If any issues require a merge, refer to the **Merging** section.

#### Pushing
After committing, you must push your code to the repository to save your changes. Simply type:

`git push`

and you will be prompted for your username and password. *Use teamursamajor's username and password.*

It's a good practice to commit often and push your commits at the end of the day. This is your responsibility. *Sign your commits with your name or your programming name at the end of each commit.*

#### Merging
When you have edited and commited code which has also been changed on the repository, you will run into a merge conflict (occurs after you commit code then pull changes).

Merge conflicts are recognized by VS Code. The differences are highlighted and there are inline actions that allow you to accept current changes, accept incoming changes, accept both, or compare both. Look for the mess of >>>>>, =====, <<<<<, and various other text/red lines. Head refers to your local changes and Test refers to the incoming changes from the repo. 

Keep the code you want to commit and delete any code or text you do not want. If you're unsure if some code is necessary, ask others or use your intuition. If you make the wrong decision, we can use Git to reclaim any lost code, so don't worry about it. (Yay for version control!)

There are special programs called Merge Tools that will handle this process much better, but they will not be covered here. Meld is a good one, if you want to try one.

#### Using Git in VS Code
Now, generally you should always use Git Bash and the terminal for your version control needs. However, **after** you have learned to use all of the commands above, you can use the VS Code integration with Git for convenience.

First, look at the menu on the left side of VS Code. Click on the third option (the one that looks like a "Y"). This is the source control menu. It will show you any changes that you have made while editing. These changes are not yet staged for commit. To do so, hover over them and click on the "+." You can also click on the first icon to open the file in the editor or the second icon to discard changes (**be very careful so you don't accidentally discard all the work you just did**). You can also hover over the "Changes" bar and click on the "+" to stage all files for commit.

Next, you can type the commit message in the box above it, then press CTRL + Enter to commit it. You can also click on the check mark to commmit. If you need to perform any other action (like pull, push, undo last commit, etc...) click on the ellipse ("...") and select the option you need from the drop down menu.

If you need to refresh so that VS Code recognizes new changes, just click on the Refresh icon in the middle of the bar. (**Do NOT press F5; in VS Code F5 will attempt to run and debug your code, not refresh!**)

#### Hardware Overview
If you’re ever confused about the function of any hardware on the eboard, look at https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/599672-frc-control-system-hardware-overview.

#### Creating a New Git Repository
*This section is only relevant to future programming leaders.*
If you're the sorry soul who needs to create next year's code repository, have no fear! Creating a new repository is simple if you follow these instructions.

First, make sure you've installed Git Bash, VS Code, and WPILib. Then, open VS Code and press CTRL + Shift + P to show all commands. Search for "Create a new project" and open up the WPILib project creator. Set your project type to Template, your language to Java, and your project base to Timed or Command Robot. Select a project folder on your personal computer (I'd recommend C:/Users/[your username]/2021\_RobotCode\_2849), set your project name to "2021\_RobotCode\_2849", and set the team number to 2849. Click "Generate project."

Next, open up a Git Bash terminal (CTRL + SHIFT + \`). Make sure your current directory is in your project folder (if not, type `cd ~/2021_RobotCode_2849`). Type `git init` to initialize the local directory as a Git repository. Type `git add .` to stage all the files you just generated for your commit. Then type `git commit -m "First commit"` to commit your changes.

After committing, type `git remote add origin https://github.com/teamursamajor/2020_RobotCode_2849.git` to set the new remote repository where your local repository will be pushed. Finally, type `git push origin master` to push your commit to the remote repository.

Note that all of your files will be stored in a folder nested inside of the original project folder. Trust me, I tried to get around this but couldn't. Just put up with it. If anything goes horribly wrong, close VS Code, delete the project folder entirely, open VS Code again, open a git terminal, and type `git clone https://github.com/teamursamajor/2020_RobotCode_2849` to add the repo back to your computer. Best of luck!
