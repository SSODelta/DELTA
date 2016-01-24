# DELTA

[**To check the old README.old, click here**](https://github.com/SSODelta/DELTA/blob/master/README_old.md)

DELTA is an extensive library for Java 8.0 primarily for working more easily with colors than is possible in the standard Java library. Though Java has support for HSB -> RGB and RGB -> HSB conversion, it is quite messy to use Color.getHSBColor(double h, double s, double b) every time. In DELTA it is much easier to make color space conversions, using the Colour class. Let's do an example:

    Colour c = Colour.RED;
    
    System.out.println(c); //RGB: [1.0, 0.0, 0.0]
    
    c.convert(ColourSpace.HSV);
    System.out.println(c); //HSV: [0.0, 1.0, 1.0]
    
    c.convert(ColourSpace.CMYK);
    System.out.println(c); //CMYK: [0.0, 1.0, 1.0, 0.0]

As you can see, it is very simple to convert between color spaces. A ColourSpace is a class that implements the ColourSpace interface, which contains two instructions: toRGB(double[] data) and fromRGB(double[] rgb). The advantage of this becomes clear if you've ever had to change to hue of something in RGB. Simply do:

    Colour c = ...; //Some RGB color
    c.convert(ColourSpace.HSV);
    c.add(hueChange, 0, 0);
    c.convert(ColourSpace.RGB);

Very fancy! Notice how the class is spelled in the British way; Colour - with a U. This is to ensure compatibility with the java.awt.Color object, which is used frequently when making programs that need to manipulate colors. Of course, DELTA makes conversion between the standard library java.awt.Color and the com.delta2.colours.Colour object. Simply do:

    Color awt = ... ;
    
    Colour c = new Colour(awt); //Convert to DELTA Colour
    
    Color  d = c.toColor(); //Convert back to java.awt.Color
    
## DELTA Images

The next logical step would be including images as well to implement powerful image manipulation methods based on the Colour object, and of course DELTA has just this. A DELTA Image is implemented in the class DImage and contains a 2-dimensional Colour array at its heart. To use a DImage, either convert from a BufferedImage:

    BufferedImage buff = ... ;
    DImage img = new DImage(buff);

Or read directly from the system using the DImageIO-class, which works as an adapter between the DImage-class and the ImageIO / BufferedImage-classes from the standard library. So:

    DImage img = DImageIO.read(new File(path));
    
The power of DImages comes largely from the ability to apply image filters to images. A DELTA Image Filter is implemented in the DImageFilter class, which is an interface containing a single instruction; public Colour filter(int x, int y, Colour[][] raster);.

To apply a filter to a DImage, simply use the .applyFilter() command:

    DImage img = ... ;
    img.applyFilter( filter );
    
DELTA contains a number of pre-written filters, which are all contained in the subpackage com.delta2.colours.filters.image.

![Showcasing several DELTA filters on an image of tropical birds](https://dl.dropboxusercontent.com/u/19633784/birds/delta%20birds.png)

As well as filters, DELTA also contains support for alpha-compositing (i.e. blend-modes) images of different transparency using the .blend() command.

## DELTA animations 

DELTA also has support for easily creating animations from existing animations or still images. An animation in DELTA is implemented in the Animation class.
