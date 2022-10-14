# Create Objects example
The purpose of this repository is to create an example inside a local instance Liferay.
The example consists in:
* Universities: Contains students and subjects.
* Students: Can only have 1 university and N subjects.
* Subjects: Can be taught by N universities and attended by M students.

It will create:
* The object definitions
* The relationships between the objects
* The layouts of the objects
* Data of each object

In the future, The application should ask if the Students should be a Custom Object or a System Object.

### How to use it
Download the repository and add to your $PATH the `createObjects` script inside tool folder. It will execute the jar of the same folder.
You need to have started a Liferay server.