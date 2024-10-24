<!--
I highly recommend viewing this file with a markdown viewer
-->

### Installation and Gameplay

To play the game, first download [Java](https://www.oracle.com/java/technologies/downloads/) 
for your operating system. Next, clone or download [this repository](https://github.com/TheHankMachine/UNI_CBL.git).
Lastly, run the [main](Main.java) file. 

Doing this will open up the game window. From here, you can click on the start
button to start the game. During gameplay, you can press space to shoot and use the 
mouse to control the plane.

### About the project

For our CBl, we decided to make a simple arcade shooter akin to the arcade game Time Pilot
(Henry's favourite arcade game). The project was split up into two parts: the engine and the 
game. Henry was in charge of creating a game framework which Jakub would then build a game in.

<!--
add more here
-->

### Advanced Topic I: Advanced Object-Oriented Programming

#### TODO: Finish

### Advanced Topic II: Developing A Game Framework

I looked into developing a lightweight game framework in Java using the Java Swing.
I decided to base my framework off of [Phaser](https://github.com/phaserjs/phaser), 
a JavaScript game framework that I am somewhat fond of.

Here are some of the features that I included in the framework:

 * A simple rendering pipeline was set up to call a [```draw```](engine/render/Renderable.java) 
for all objects which implement the [Renderable](engine/render/Renderable.java) class.
This is done by registering objects to a renderlist which is iterated through on JPanel repaint.


 * A similar thing to rendering was done for input handling, where any class implementing
[Updateable](engine/update/Updateable.java) can be registered to an update list. Every tick,
the method [```update```](engine/update/Updateable.java) is called for each element in that list.


 * [Sprites](engine/render/Sprite.java) and [spritesheet](engine/render/SpriteSheet.java) classes. 
These classes streamline loading image files and drawing them to the screen. Many of the methods
in these classes' parent class [DisplayObject](engine/render/DisplayObject.java) were inspired by
[Phaser](https://github.com/phaserjs/phaser).


 * The input [InputHandler](engine/input/InputHandler.java) class attaches key and mouse
listeners to the frame which allow for the creation of functions such as
[```isDown```](engine/input/InputHandler.java) and 
[```getMousePositionScreenRelative```](engine/input/InputHandler.java).


Other notable utility classes include: [Vector2D](engine/math/Vector2D.java), 
[SpriteFont](engine/render/SpriteFont.java), and [Game](engine/Game.java).

These features are meant to streamline game development by abstracting and streamlining
code such as drawing to the screen.