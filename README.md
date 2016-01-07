# DELTA

## Colors
DELTA adds extensive support for colors, making it trivial to convert between various color spaces. 
Any color in DELTA is a subclass of com.delta.colors.Color, which itself is an abstract class. We can define a bright red color as:

    Color red = new RGB(255, 0, 0);

And then convert this to various color spaces:

    red.toCMYK().toString(); //CMYK: [0.0, 1.0, 1.0, 0.0]
    red.toHSV().toString();  //HSV:  [0.0, 1.0, 1.0]
    red.toLMS().toString();  //LMS:  [17.8824, 3.4557, 0.02996]
    red.toYIQ().toString();  //YIQ:  [0.299, 0.596, 0.211]

To preserve compatibility with existing Java-methods, the DELTA Color-library makes it easy to convert to java.awt.Color.
So we can for instance do:

    java.awt.Color awtRed = red.toAWTColor();

And it's also trivial to construct a DELTA Color from a java.awt.Color.

    Color red2 = Color.fromAWTColor(awtRed);

### Images
The natural extension of a Color class is an Image-class, so of course this library contains an Image-class. 
Suppose we have loaded some image from the hard disk (possibly using ImageIO) as a BufferedImage, then we can create a DELTA Image:

    Image img = new Image(bufferedImage);

Converting back to a BufferedImage is also trivial:

    BufferedImage bufferedImage2 = img.getBufferedImage();

But why would we ever do this? You see, the advantage of using a DELTA Image becomes clear when we want to manipulate our images some way...

### Blending images

DELTA supports blending images using all the regular blend modes from Photoshop and other image editing software.

### Filters
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
This is a very powerful feature. Suppose we want to shift the hue of some Image:

    Image img = . . .
    Image blurredImage = img.applyFilter( (x,y,raster) -> raster[x][y].toHSV().add( Math.max(x, y), 0, 0) );

![Shifting the hue by max(x,y)](https://dl.dropboxusercontent.com/u/19633784/birds/output.png)

Notice the lambda expression in place of an anonymous class definition.

DELTA includes several filters already programmed, so the following are all legal statements:

    Image blurredImage = img.applyFilter( new SimpleBlurFilter( 9 ) );

![Blurring the image](https://dl.dropboxusercontent.com/u/19633784/birds/blur.png)

    Image medianImage = img.applyFilter( new MedianFilter( 11 ) );

![Adding a median filter](https://dl.dropboxusercontent.com/u/19633784/birds/median.png)

    Image motionBlurredImage = img.applyFilter( new MotionBlurFilter( Math.PI/4, 9 ) );

![Adding motion blur](https://dl.dropboxusercontent.com/u/19633784/birds/motionblur.png)

    Image edgeDetectImage = img.applyFilter( new SimpleEdgeDetectionFilter() );

![Edge detection](https://dl.dropboxusercontent.com/u/19633784/birds/edges.png)

Or, we could combine several of these:

    Image comboImage = 
    img.blend(
        img.applyFilter( new MotionBlurFilter(Math.PI/4,9))
           .applyFilter( new MedianFilter(11))
        , BlendMode.SCREEN);

![A combination image](https://dl.dropboxusercontent.com/u/19633784/birds/combo.png)

All credit for the implementations of these filters goes to Lode Vandevenne (http://lodev.org/cgtutor/filtering.html).
