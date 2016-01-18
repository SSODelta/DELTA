# DELTA

[**To check the old README.old, click here**](https://github.com/SSODelta/DELTA/blob/master/README_old.md)

As of 18/01/2016, DELTA engine has been rewritten completely. This was because of garbage collection issues that come from creating new instances of objects all the time. In the old engine, when you did

    Image img = ...
    Image img2 = img.applyFilter( ... );

... the object img2 would be a distinct object from img - that is, the images were immutable and all functions were without side effect. Initially, this seemed like a fantastic idea, because it makes it easy to string together transformations to make more complex filters:

    img.blend( img.applyFilter( ... ).applyFilter( ... ).applyFilter( ... ) );

Unfortunately, after trying to render some more demanding filters and longer animations, I encountered some memory issues; I got a ["GC Overhead limit exceeded"](https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/memleaks002.html) error. This means the Java garbage collector is using more than 98% of its time garbage collecting - and doing a poor job. Naturally, this is because the code generated too many distinct objects that are interconnected in complex ways (because some objects get cloned properly and others not). I examined the code and decided it was unsalvageable, and rewrote the engine completely with side effect to make the code more efficient.

At the same time, I have also changed how the Colour-class itself works. Before, there was an abstract superclass, Colour, and then each colour space extended this superclass and ensured conversion to other colour spaces. Now, however, the class Colour is not abstract and a ColourSpace interface has been created, which contains two methods; fromRGB() and toRGB(). I have also renamed the Image class to DImage to ensure compatability with the Java standard library. Minor differences, I know, but it makes the code cleaner.

I still need to 
