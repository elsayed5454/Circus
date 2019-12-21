package eg.edu.alexu.csd.oop.game.model.DynamicLinkage;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DynamicLinkage {
    private final String jarPath = new File("").getAbsolutePath() + "\\Jars" ;
    private LinkedList<Class<?>> Shapes = new LinkedList<>();
    private GameLogger gameLogger = GameLogger.getInstance();

    public DynamicLinkage() {}

    public LinkedList<Class<?>> loadShapesClasses(List<String> jars) throws IOException, ClassNotFoundException {
        File jarDir = new File(jarPath);
        for(File file : Objects.requireNonNull(jarDir.listFiles())) {
            if (jars.contains(file.getName())){
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> e = jar.entries();

                URL[] urls = {new URL("jar:file:" + file.getPath() + "!/")};
                URLClassLoader classLoader = URLClassLoader.newInstance(urls);

                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class")) {
                        continue;
                    }

                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class<?> clazz = classLoader.loadClass(className);

                    if (!clazz.isInterface()) {
                        Shapes.add(clazz);
                    }
                }
            }
        }
        gameLogger.log.debug(" The Jar is Successfully loaded ");
        return Shapes;
    }
}
