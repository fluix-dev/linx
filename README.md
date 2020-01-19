<h1>linx</h1>
<p align=center>
  <br>
  <span>A decentralized, self-healing network framework.</span>
  <br>
    <a href="#about">About</a>
    &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#installation">Installation</a>
    &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#future">Future</a>
  <br><br>
  <img src="https://i.imgur.com/b9OfsAi.gif">
</p>

## About
Throughout our lives, we have often encountered situations where a single, central server went down and all communication ceased to exist. We want to change this through the use of a network framework that increases stability, decreases downtime, and allows for more accessible applications.

This application replaces the normal, central server with many clients model, and uses a type of mesh network where many smaller servers or hubs group together clients or nodes into a tree. Packets are propogated up and down the tree and stored on all severs for redundancy.

Throughout our development, we came across multiple challenges, primarily involving packet management in the main application. We needed to make sure that packets could make it up and down the network tree but not be re-broadcasted in an infinite loop. This issue took a substantial amount of time to fix, but we succeeded in the end and are proud that we did.

## Installation
 - Install Java 8
 - Install `kryonet` and add it to the build path.
 - Run the Node class using `java` with the `-classpath` argument set to the debug/production jar of `kryonet`.

## Future
The future of linx is to develop more packets to allow for better privacy through encryption and improved stability. A re-write of the Java code may also occur to improve structure. A website that could present a live view of the running network is also a goal we would like to achieve.

#### Created during StarterHacks 2020
