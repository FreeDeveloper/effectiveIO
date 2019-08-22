package cn.smile.io.lambda;

/**
 *Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
 *
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 *
 * 使用 Lambda 表达式可以使代码变的更加简洁紧凑。
 * */
public class LambdaTest {

    final static String salutation = "Hello! ";

    public static void main(String [] args){
        /**
         * // 1. 不需要参数,返回值为 5
         * () -> 5
         *
         * // 2. 接收一个参数(数字类型),返回其2倍的值
         * x -> 2 * x
         *
         * // 3. 接受2个参数(数字),并返回他们的差值
         * (x, y) -> x – y
         *
         * // 4. 接收2个int型整数,返回他们的和
         * (int x, int y) -> x + y
         *
         * // 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
         * (String s) -> System.out.print(s)
         *
         * */

        LambdaTest lambdaTest = new LambdaTest();

        //类型声明
        MathOperation addition = (int a, int b) -> a + b;

        //不用类型声明
        MathOperation subtraction = (a,b) -> a-b;

        //大括号中的返回语句
        MathOperation multiplication = (int a,int b) -> {return a * b;};

        //没有大括号中的返回语句
        MathOperation division = (int a,int b) -> a/b;

        System.out.println("10 + 5 = "+lambdaTest.operate(10,5,addition));
        System.out.println("10 - 5 = "+lambdaTest.operate(10,5,subtraction));
        System.out.println("10 * 5 = "+lambdaTest.operate(10,5,multiplication));
        System.out.println("10 / 5 = "+lambdaTest.operate(10,5,division));

        //不用括号
        GreetingService greetingService = message ->
                System.out.println("hello "+message);

        //用括号
        GreetingService greetingService1 = (message) ->
                System.out.println("hello "+message);

        greetingService.sayMessage("Runoob");
        greetingService1.sayMessage("Google");

        /**
         * 1、Lambda 表达式主要用来定义行内执行的方法类型接口，例如，一个简单方法接口。在上面例子中，我们使用各种类型的Lambda表达式来定义MathOperation接口的方法。然后我们定义了sayMessage的执行。
         * 2、Lambda 表达式免去了使用匿名方法的麻烦，并且给予Java简单但是强大的函数化的编程能力。
         * */

        /**
         * lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，
         * */

        GreetingService greetingService2 = message ->
                System.out.println(salutation + " " + message);
        greetingService2.sayMessage("Rubbo");

        /**
         * 可以直接在 lambda 表达式中访问外层的局部变量
         * */

        final int num = 1;

        Converter<Integer,String> converter = param ->
                System.out.println(param + num);

        converter.convert(2);


        int num2 = 2;

        Converter<Integer,String> converter2 = param ->
                System.out.println(param + num2);

        converter2.convert(2);

        /**
         *lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
         * */

        //        num2 = 2;  加上此行后编译出错
        //报错信息：Local variable num defined in an enclosing scope must be final or effectively final

        /**
         * 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量
         * */
        String first = "";
//        Comparator<String> comparator = (first, second) -> Integer.compare(first.length(), second.length());  //first冲突，编译会出错

    }

    public interface Converter<T1, T2> {
        void convert(int i);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

}
