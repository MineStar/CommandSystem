package de.minestar.maventest.commandsystem;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParameterList {

    // TODO: Comment!

    private final Object[] args;
    private final int offset;

    public ParameterList(Object[] args) {
        this.args = args;
        this.offset = 0;
    }

    public ParameterList(ParameterList pList, int offset) {
        this.args = pList.args;
        this.offset = offset;
    }

    public int length() {
        return args.length - offset;
    }

    public boolean isEmpty() {
        return length() <= 0;
    }

    public Boolean getBoolean(int index) {
        String arg = args[index].toString();
        if (arg.equals("0") || arg.equalsIgnoreCase("false"))
            return Boolean.FALSE;
        else if (arg.equals("1") || arg.equalsIgnoreCase("true"))
            return Boolean.TRUE;
        else
            return null;
    }

    public Boolean getBoolean(int index, Boolean defaultValue) {
        Boolean result = getBoolean(index);
        return result != null ? result : defaultValue;
    }

    public Byte getByte(int index) {
        try {
            return Byte.parseByte(args[index].toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Byte getByte(int index, Byte defaultValue) {
        Byte result = getByte(index);
        return result != null ? result : defaultValue;
    }

    public Short getShort(int index) {
        try {
            return Short.parseShort(args[index].toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Short getShort(int index, Short defaultValue) {
        Short result = getShort(index);
        return result != null ? result : defaultValue;
    }

    public Integer getInt(int index) {
        try {
            return Integer.parseInt(args[index].toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getInt(int index, Integer defaultValue) {
        Integer result = getInt(index);
        return result != null ? result : defaultValue;
    }

    public Long getLong(int index) {
        try {
            return Long.parseLong(args[index].toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getLong(int index, Long defaultValue) {
        Long result = getLong(index);
        return result != null ? result : defaultValue;
    }

    public Character getChar(int index) {
        try {
            return args[index].toString().charAt(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Character getChar(int index, Character defaultValue) {
        Character result = getChar(index);
        return result != null ? result : defaultValue;
    }

    public String getString(int index) {
        try {
            return args[index].toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getString(int index, String defaultValue) {
        String result = getString(index);
        return result != null ? result : defaultValue;
    }

    // START GENERIC SHIT

    // MAPS HOLDING ALL GETTER METHODS
    private final static Map<Class<?>, Method> getMethodMap = new HashMap<Class<?>, Method>();
    private final static Map<Class<?>, Method> getDefaultMethodMap = new HashMap<Class<?>, Method>();

    static {
        // GET ALL DECLARED METHODS OF THE PARAMETER LIST
        Method[] methods = ParameterList.class.getDeclaredMethods();
        for (Method method : methods) {
            // ONLY GET THE GETTER
            if (method.getName().startsWith("get")) {
                // GET THE PARAMATER CLASSES - INTERESTING GETTER HAS ONLY
                // "int index" OR "int index, T defaultValue"
                Class<?>[] parameterClasses = method.getParameterTypes();
                // GETTER WITHOUT DEFAULT VALUE
                if (parameterClasses.length == 1 && parameterClasses[0].equals(int.class))
                    getMethodMap.put(method.getReturnType(), method);
                // GETTER WITHOUT DEFAULT VALUE
                else if (parameterClasses.length == 2 && parameterClasses[0].equals(int.class) && parameterClasses[1].equals(method.getReturnType()))
                    getDefaultMethodMap.put(method.getReturnType(), method);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, int index) {
        try {
            Method method = getMethodMap.get(clazz);
            if (method != null)
                return (T) method.invoke(this, index);
        } catch (Exception e) {
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, int index, T defaultValue) {
        try {
            Method method = getDefaultMethodMap.get(clazz);
            if (method != null)
                return (T) method.invoke(this, index, defaultValue);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    // DEBUG
    public static void main(String[] args) {
        ParameterList pList = new ParameterList(new Object[]{2, 3});
        Integer i = pList.get(Integer.class, 0);
        System.out.println(i);

        ParameterList pList2 = new ParameterList(new Object[]{"String", "Kilian"});
        String s = pList2.get(String.class, 1);

        System.out.println(s);
        s = pList2.get(String.class, 3, "Lol");
        System.out.println(s);

        Iterator<String> iter = pList2.getIterator(String.class, 1);
        while (iter.hasNext())
            System.out.println(iter.next());

        Iterator<String> iter2 = pList2.getIterator(String.class, 0);
        while (iter2.hasNext())
            System.out.println(iter2.next());
    }

    public <T> Iterator<T> getIterator(Class<T> clazz, int index) {
        return new ParameterIterator<T>(clazz, index);
    }

    private class ParameterIterator<T> implements Iterator<T> {

        private int index;
        private Class<T> clazz;

        public ParameterIterator(Class<T> clazz, int index) {
            this.index = index + offset;
            this.clazz = clazz;

        }

        @Override
        public boolean hasNext() {
            return index < length();
        }

        @Override
        public T next() {
            return get(clazz, index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
