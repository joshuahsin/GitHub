# Leetcode Problems (7/1 - 10/3)
<img width="768" alt="Screen Shot 2022-10-03 at 4 17 39 PM" src="https://user-images.githubusercontent.com/32546754/193702553-92e9889d-a45d-4e3b-8027-62daf8aaae32.png">


# Hard Problems
## Median of Two Sorted Arrays
<img width="423" alt="Screen Shot 2022-10-03 at 4 56 26 PM" src="https://user-images.githubusercontent.com/32546754/193706201-2c9b5225-ba7c-4690-8c32-c2682d1812b9.png">

### Constraints
<img width="334" alt="Screen Shot 2022-10-03 at 4 56 43 PM" src="https://user-images.githubusercontent.com/32546754/193706227-62af9c88-4afa-4d64-8fa0-a1eb1f009ac0.png">

### Examples
<img width="455" alt="Screen Shot 2022-10-03 at 4 56 59 PM" src="https://user-images.githubusercontent.com/32546754/193706255-5543e388-8d39-4829-8ba0-06eb4dc55b5b.png">

### Solution in C
<img width="700" alt="Screen Shot 2022-10-03 at 5 00 10 PM" src="https://user-images.githubusercontent.com/32546754/193706558-51204a76-ac68-4a20-bdcd-9bb4f9cec3d3.png">

To solve this problem, I first created an integer array of the size of the floor of half the sum of the size of both arrays + 1. I only made the array half the size of the total merged array 
since I only needed the middle value/values of the larger sorted array. I then initialized two integer values representing the current index of both arrays. After this, I iterated through a 
loop the same amount of times as the size of the array I created, adding  the current smallest value between the two arrays to the array and incrementing the corresponding array index each 
time. Following this, if the total merged array had an odd length, I just returned value of the last index of the half array, as that would be the only middle value. If the total merged array  
had an even length, I took the average of the last two values in the half array, as they would be the two middle values.

### Time Complexity
Everything before the loop is constant O(1). The loop runs in O(m + n / 2) time if m is equal to the size of nums1 array and n is equal to the size of nums2 array. After the loop, everything 
is also constant time. Therefore, the Overall time complexity is O(m + n / 2).

### Space Complexity
The overall space complexity is O(m + n / 2).

<br>

## Merge k Sorted Lists
<img width="440" alt="Screen Shot 2022-10-03 at 8 04 14 PM" src="https://user-images.githubusercontent.com/32546754/193725624-85cb9189-9a05-42d3-80b9-318b60464509.png">

### Constraints
<img width="366" alt="Screen Shot 2022-10-03 at 8 05 19 PM" src="https://user-images.githubusercontent.com/32546754/193725739-5e81bf52-07da-4eb2-a213-ea1f7ca7a441.png">

### Examples
<img width="440" alt="Screen Shot 2022-10-03 at 8 05 41 PM" src="https://user-images.githubusercontent.com/32546754/193725782-326df080-7971-4ed3-b4e2-e2471ed43d56.png">

### Solution in C++
<img width="509" alt="Screen Shot 2022-10-03 at 8 07 02 PM" src="https://user-images.githubusercontent.com/32546754/193725915-1551def1-b483-4c24-978d-ba3d64354d52.png">

To solve this problem, first iterate through the vector of LinkedLists. For every value in each LinikedList, insert the value into a vector of integers. If the vector is empty, return NULL. 
If the vector is not empty, sort the vector. Next, create a head ListNode with the first value and a tracking ListNode to add to the head and move after adding a ListNode. Iterate through 
the rest of the vector of sorted values and add on the the linked list using the tracking ListNode. Finally, return the head ListNode.

### Time Complexity
Assume n is the total number of values in all LinkedLists. The first loop would O(n) to loop through all values and insert them into the vector nums. Sorting this vector using C++'s default 
would be O(n log n). The last loop to append all values to the LinkedList which is returned is O(n) complexity. The overall time complexity is O(n log n).

### Space Complexity
The overall space complexity is O(n).

<br>

## Trapping Rain Water
<img width="537" alt="Screen Shot 2022-10-03 at 9 41 43 PM" src="https://user-images.githubusercontent.com/32546754/193735908-6c08d766-dbf9-434d-ac5a-2af48a0a3185.png">

### Constraints
<img width="228" alt="Screen Shot 2022-10-03 at 9 42 49 PM" src="https://user-images.githubusercontent.com/32546754/193735980-06e1ba85-8fa6-4ebb-8b3d-f259f7fb4848.png">

### Examples
<img width="530" alt="Screen Shot 2022-10-03 at 9 45 03 PM" src="https://user-images.githubusercontent.com/32546754/193736247-a1d825c2-df35-40e4-90e8-f135990f22b3.png">

### Solution in C++
<img width="463" alt="Screen Shot 2022-10-04 at 9 44 13 PM" src="https://user-images.githubusercontent.com/32546754/193983103-76921fd7-327a-4d02-a8c8-b708554dd989.png">

- Start with left and right trackers and left and right wall variables to keep track of the current greatest values on the left and right side. Then loop through a while loop to calculate the result 
while left tracker is less than right tracker. 
- In the while loop, check if the current height of left is greater than or equal to the current height of right and the height of left is greater than the 
current left_wall, set the new left_wall to the left height. If the current left height is less than the left wall height, add the difference between the left_wall and the current left value to result 
as the water is filling from the left wall towards the right. Then increment left by 1. 
- If current right height is greater than the left wall and the current right height is greater than right_wall, set 
right_wall to the current right height. If the current right height is less than than the right wall, that means water is filling from the right wall towards the left. Add the difference between the 
right_wall and the current right height to the result. Then decrement right by one.
- After the while loop, return the result.

### Time Complexity
Setting the variables is O(1) time. Assuming the length of height is n, the loop is O(n) time complexity, as it runs through n times. So, the overall time complexity is O(n).

### Space Complexity
The space complexity is O(1).

<br>

# Medium Problems
## String to Integer (atoi)
<img width="524" alt="Screen Shot 2022-10-03 at 8 38 08 PM" src="https://user-images.githubusercontent.com/32546754/193729118-c6832a66-f373-4d22-ab05-83396e65b402.png">

### Constraints
<img width="505" alt="Screen Shot 2022-10-03 at 8 38 39 PM" src="https://user-images.githubusercontent.com/32546754/193729176-42dcddea-67fd-47f9-9409-5296a5ac768a.png">

### Examples
<img width="751" alt="Screen Shot 2022-10-03 at 8 39 14 PM" src="https://user-images.githubusercontent.com/32546754/193729248-7914e980-96a7-4593-bf21-0c3ab2d90dc8.png">
<img width="751" alt="Screen Shot 2022-10-03 at 8 39 26 PM" src="https://user-images.githubusercontent.com/32546754/193729267-580eb453-0b44-4c91-892b-332af4cf591e.png">

### Solution in C++
<img width="595" alt="Screen Shot 2022-10-03 at 8 41 06 PM" src="https://user-images.githubusercontent.com/32546754/193729471-06b98de3-7326-4087-a9db-937423847907.png">
To solve this problem, first, initialize a character array of digits. Next, iterate through all characters of the string s. First, skip all white spaces. If the first non white space is '-', change 
the boolean negatigve varibale to true. If the first character is not '+' or '-', set result equal to the first digit. For the rest of the characters, if the character is a digit, first check if the 
result would be greater than the integer maximum value. If so, set the boolean overflow variable to false. Else, multiply the current result by 10 and add the new digit. Finally, return the result 
based on the values of result, and value of the overflow and negative boolean variables.

### Time Complexity
Setting all the variables is O(1) complexity. Assuming the string s has length n, iterating through all characters in the string is O(n) time complexity. The final checks and returns are O(1) time 
complexity, so the overall time complexity is O(n).

### Space Complexity
The overall space complexity is O(1).

<br>

## Walking Robot Simulation II
<img width="525" alt="Screen Shot 2022-10-04 at 9 27 06 PM" src="https://user-images.githubusercontent.com/32546754/193981303-1841c24e-5213-475f-b6f7-775965f0692f.png">

### Constraints
<img width="520" alt="Screen Shot 2022-10-04 at 9 27 44 PM" src="https://user-images.githubusercontent.com/32546754/193981360-df3af632-8d3c-4bc4-bd76-94e4c35ffeae.png">

### Examples
<img width="519" alt="Screen Shot 2022-10-04 at 9 28 11 PM" src="https://user-images.githubusercontent.com/32546754/193981415-15e65478-091d-4861-b97c-27fb6c948824.png">
<img width="498" alt="Screen Shot 2022-10-04 at 9 28 36 PM" src="https://user-images.githubusercontent.com/32546754/193981457-4d76a778-747b-471e-a488-df6681086187.png">

### Solution in C++
<img width="516" alt="Screen Shot 2022-10-04 at 9 42 51 PM" src="https://user-images.githubusercontent.com/32546754/193982893-c1b39286-a619-435d-97a4-f2bbfe7caf4b.png">
<img width="435" alt="Screen Shot 2022-10-04 at 9 43 10 PM" src="https://user-images.githubusercontent.com/32546754/193982916-66e58646-d65d-481a-a834-07948071c428.png">
<img width="256" alt="Screen Shot 2022-10-04 at 9 47 35 PM" src="https://user-images.githubusercontent.com/32546754/193983363-47bb376b-8705-4ecb-a4ca-0a724f010b9c.png">

To solve this problem, I initalized private integer instance variables representing the grid width, grid height, and perimeter size, a string instance variable represeting the direction the robot is facing, and a integer vector represting the coordinate position of the robot. Upon construction of Robot object, set the variables, calculate the perimeter, set the position of the robot to (0, 0), and set the direction to "East". For the getters getPos and getDir, return the integer vector position and the String direction. To move the robot, first check if the number of steps is greater than the perimeter. If so, the moving the robot by the mod of the number of steps is the same as moving the robot by that number of steps. If so, set the direction to South, as that would be the correction direction after iterating through the perimeter. This is your new number of steps. If the number of steps is less than or equal to perimeter, do nothing. After this, loop through a while loop, moving the robot along each direction by the length of the side and turning it towards the correspoding direction. Keep doing this while subtracting the steps by the number of spaces traveled on each side until the number of steps is completed (At this point the current number of steps will be less than or equal to the size of that grid side. 

### Time Complexity
The Constructor and getPos and getDir functions are O(1). The step function is O(1) for the if(num > perimeter) prechecking and the while loop is O(steps / ((length + width) / 2)) for the number of sides traversed and the constant work done on every iteration. So the step function is O(steps / ((length + width) / 2)) overall time complexity.

### Space Complexity
The space complexity for all methods and the constructor is O(1).
