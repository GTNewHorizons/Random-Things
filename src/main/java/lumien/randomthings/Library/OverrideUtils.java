package lumien.randomthings.Library;

public class OverrideUtils {
    public static Class[] convertToClassArray(Object[] objectArray) {
        Class[] classArray = new Class[objectArray.length];
        for (int i = 0; i < objectArray.length; i++) {
            classArray[i] = objectArray[i].getClass();
        }
        return classArray;
    }
}
