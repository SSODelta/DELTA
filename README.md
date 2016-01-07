# DELTA

## Colors
DELTA adds extensive support for colors, making it trivial to convert between various color spaces. 
Any color in DELTA is an instance of a *color space*. All color spaces are subclass of com.delta.colors.Colour. The Colour class is an abstract class containing a vector representing the values of the color channels in the color space extending Colour. So RGB is a subclass of Colour, which implements the Red-Green-Blue color space. RGB is not abstract so we can invoke its constructor to create a new Colour object.

    Colour red = new RGB(255, 0, 0);

And then convert this to various color spaces:

    red.toCMYK().toString(); //CMYK: [0.0, 1.0, 1.0, 0.0]
    red.toHSV().toString();  //HSV:  [0.0, 1.0, 1.0]
    red.toLMS().toString();  //LMS:  [17.8824, 3.4557, 0.02996]
    red.toYIQ().toString();  //YIQ:  [0.299, 0.596, 0.211]

To preserve compatibility with existing Java-methods, the DELTA Colour-library makes it easy to convert to java.awt.Color.
So we can for instance do:

    java.awt.Color awtRed = red.toAWTColor();

And it's also trivial to construct a DELTA Colour from a java.awt.Color.

    Colour red2 = Color.fromAWTColor(awtRed);

## Images
The natural extension of a Color class is an Image-class, which DELTA also contains an implementation of.
Suppose we have loaded some image from the hard disk (possibly using ImageIO) as a BufferedImage, then we can create a DELTA Image:

    Image img = new Image(bufferedImage);

Converting back to a BufferedImage is also trivial:

    BufferedImage bufferedImage2 = img.getBufferedImage();

The advantage of using a DELTA Image becomes clear when we want to manipulate our images. To perform an operation on every pixel in a BufferedImage a user would need to iterate through every (x,y) coordinate and and update the pixel (unless you use a BufferedImageOp, but we do not discuss those here). The problem gets worse if we want to apply different operations to different images - we would need to rewrite the loops to implement new functionality.

### Blending images

DELTA supports blending images using all the regular blend modes from Photoshop and other image editing software. This means that any two images can be blended together by supplying a BlendMode-object.

    Image img1 = . . ., img2 = . . .;
    Image blend = img1.blend(img2, BlendMode.OVERLAY);

In this case, img1 is the 'target' and img2 is the 'blend' [as described by Rolegio B. Andreo](http://www.deepskycolors.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html). 

You can also change the alpha channel of an Image object, which changes how dominant the colors from a given image are. This is also exactly analogous with Photoshop or other editing software.

### Image filters
Suppose we want to apply a filter to
an image that shifts the hue of each pixel by some amount. Using a regular BufferedImage we first have to construct a loop that enumerates
all pixels in the image, then convert the pixels to the HSV (or HSL) color space, add some value to the hue and convert it back to RGB before
finally updating the pixel in the BufferedImage.
This is a lot of a hassle for a (possible) simple operation on an image. Luckily, the DELTA Framework makes this process a lot easier.
There is an interface, ImageFilter:

    public interface ImageFilter {
       public Color filter(int x, int y, Color[][] raster);
    }

Which captures the concept of an image filter. We parse the location of the pixel, we wish to update and then the raster for the entire image. 
The reason to include the entire raster as an argument is to support filters where the new value of a given pixel depends on the other pixels in the image.

Suppose we have some Image:

![A regular image of some birds](https://dl.dropboxusercontent.com/u/19633784/birds/bird.jpg)

The Image class has a method .applyFilter(ImageFilter f); that applies some ImageFilter to a given image and returns the result.
This is a very powerful feature. DELTA includes several filters already programmed, so we can do:

    Image blurredImage = img.applyFilter( new SimpleBlurFilter( 9 ) );

![Blurring the image](https://dl.dropboxusercontent.com/u/19633784/birds/blur.png)

    Image medianImage = img.applyFilter( new MedianFilter( 11 ) );

![Adding a median filter](https://dl.dropboxusercontent.com/u/19633784/birds/median.png)

    Image motionBlurredImage = img.applyFilter( new MotionBlurFilter( Math.PI/4, 9 ) );

![Adding motion blur](https://dl.dropboxusercontent.com/u/19633784/birds/motionblur.png)

We are not limited to the already-implemented filters - it is also possibly to anonymously implement the ImageFilter-interface as shown; suppose we want to shift the hue of some Image:

    Image img = . . .;
    Image blurredImage = img.applyFilter( (x,y,raster) -> raster[x][y].toHSV().add( Math.max(x, y), 0, 0) );

![Shifting the hue by max(x,y)](https://dl.dropboxusercontent.com/u/19633784/birds/output.png)

Here we use a Lambda expression (introduced in Java 8.0) to anonymously define an instance of ImageFilter, which adds max(x, y) to the hue of the image.

Or, we could combine several of these filters:

    Image comboImage = 
    img.blend(
        img.applyFilter( new MotionBlurFilter(Math.PI/4,9))
           .applyFilter( new MedianFilter(11))
        , BlendMode.SCREEN);

![A combination image](https://dl.dropboxusercontent.com/u/19633784/birds/combo.png)

All credit for the implementations of these filters goes to Lode Vandevenne (http://lodev.org/cgtutor/filtering.html).

## Animations

DELTA also has built-in support for producing animations from a list of images. Say we want to animate our bird image from earlier. We can then construct a new Animation object:

    Image bird = . . .;
    Animation birdAnimation = new Animation(FRAME_LENGTH, bird);

The Animation constructor also requires the length (in milliseconds) of each frame in the output animation. Now, to do something with each frame in this animation, we need to process it. This is done using the .processAnimation()-command which returns a new Animation object where each frame is (possibly) changed. It requires two arguments: 1) the number of frames in the animation and 2) A TimeFunction object.

A TimeFunction object is just an interface:

    public interface TimeFunction{
        Image apply(Image img, double t);
    }

which describes what to do with some Image at some point in time 't'. Just like the ImageFilter it often makes sense to implement this interface anonymously (lambda expressions are recommended). An example could be:

    Animation colorfulBirds = birdAnimation.processAnimation(40, (img, t) -> 
        i.applyFilter(
            (x,y,raster) -> raster[x][y].toHSV().add(t + (x+y)/200.0,
                                                     0,
                                                     0)
        ));

![Woah!](https://dl.dropboxusercontent.com/u/19633784/birds/trippy%20fugl.gif)

### Exporting Animations

Being able to construct an Animation-object is no fun if we can't export its contents to the operating system. Fortunately, DELTA makes this very easy. Once you're satisfied with the contents of an animation object, you can invoke .export(File f); to save it in the GIF format:

    colorfulBirds.export(new File("colorfulBirds.gif"));

The method may throw an IOException if something goes wrong saving the file to the disk.

A drawback with GIF-animations is the data-loss as seen when comparing the quality of the animation to the still images. Fortunately, this can also be mitigated, BUT then you have to assemble the images yourself. If you instead invoke .export(String s); the Animation object will produce a separate image file for every Image object it contains, which can then later be assembled using time lapse software (such as ffmpeg). The Animation ensures padding the output files, so the lengths of their path names are identical (it prefixes the frame number with 0's).

[Link to .WEBM](https://dl.dropboxusercontent.com/u/19633784/birds/OUTPUT.webm)

### Importing Animations

DELTA also support constructing Animation objects from existing animations. The constructor

    public Animator(File f)

Will attempt to load a GIF file located at 'f' and read its frames into memory as an array Image objects. Invoking .processAnimation() works just the same as constructing an Animator from a single Image - the method simply "wraps around" back to frame 0 should it run out of new frames. In this sense, an Animator constructed from a single Image is a special case of loading an array of Images (where it wraps around *every* time).

Similarily, it's also possible to construct an Animator from a list of images:

    public Animator(List<Image> imageList)

### Combining Animations

Suppose you have two different Animations and want to append one of them to other. Using DELTA this is also remarkably easy:

    Animation a1 = . . ., a2 = . . .;
    Animation combined = a1.append(a2);

Which will append the frames of 'a2' *after* the frames in 'a1' and return the result as a new Animation.
