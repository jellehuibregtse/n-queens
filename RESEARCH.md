<p align="center">
  <h1 align="center">Functional Programming in the Wild</h1>
  <h4 align="center">
  <strong>Jelle Huibregtse and Aron Hemmes</strong>
  </h4>
</p>

## Research description

For this research assignment we choose a programming language, and we take a look at how functional this language is. We
chose to take a look at Java. For this research we will use
the [DOT framework](http://ictresearchmethods.nl/The_DOT_Framework). We also formulate a general research question: Is
programming in a functional manner possible in Java and to what extent? This is quite a large question, so we will
divide and conquer.

## Research questions

1. What is functional programming and what are its principles?
2. Is it possible to adhere to the functional programming principles in Java?
3. Can we solve the N Queens problem in a functional manner in Java?

## Method

Now that the questions are properly and concisely formulated, we can use the DOT Framework using the following methods
and strategies, to answer these questions. I will assign the best suited methods and strategies to the questions.

- Best, good, and bad practises (all three)
- Literature study (all three)
- Prototype (three)
- Unit test (three)

## Answers

### Functional programming

There are two fundamental programming paradigms – imperative and declarative. The former is what we would assign
Object-Oriented Programming (OOP) to and the latter to functional programming. With the imperative style of programming
we have a sequential approach; we have a sequence of steps that changes a program's state until the desired target is
reached. In imperative languages we often use elements such as variables and loops. The main takeaways from this are:
mutable state, step-by-step execution of instructions, and order of these instructions are of importance. Next, we have
declarative programming. Here, we focus on the final conditions of the desired result instead of the steps required to
achieve it. So, the final result does not depend on any external state, it's deterministic, and order is less important.

Now, let's take a look at the principles of functional programming. In functional programming, we say – like the name
suggests – that **functions are first-class citizens**. What does this mean exactly? We can say that a function is
first-class only if the following conditions are met:

- We can pass a function as an argument to another function.
- We can assign a function to a variable, just like we can assign a value to a variable.
- A function can return another function as a result.

When we talk about functions in functional programming, we talk about pure functions. This means a function has two
essential properties: **referential transparency and lack of side effects**. The first, simply means that for any given
input, the function always produces the same output. The second, means that a function's only purpose is to calculate a
result based *only* on passed arguments. The function should not mutate any state outside it. Examples are:

- Sending output to the console.
- Sending data through the network.
- Mutating variables outside the function's scope.
- Writing data to disk.

Next up are **higher-order functions**, which are functions that take another function as argument or returns one as
result. Furthermore, **immutability** is an important term when talking about functional programming. We say data is
immutable when it cannot be changed after its creation. So, no methods that allow changing state or exposing mutable
fields. The reason and benefits of this are: thread-safety, ensured, valid and constant state, and it avoids unwanted
reference modifications.

Finally, let's talk about some functional programming techniques. First, we have **function composition**, it refers to
composing complex functions from simpler (and often smaller) functions. If we have function `f: a -> b` and `g: b -> c`,
then we can compose a new function `h: a -> c`. This is function composition. Second, we have **currying**, which is a
mathematical technique of converting a function that takes multiple arguments into a sequence of functions that take a
single argument. Last, we have a powerful technique called **recursion**, this allows us to break down a problem into
smaller pieces. Furthermore, it's used to implement mathematical functions (such as the Fibonacci sequence) directly.
Moreover, it can be used for algorithm techniques such as backtracking.

To conclude, when talking about functional programming rules, we have to adhere to the following: functions are
first-class citizens, referential transparency and lack of side effects, higher-order functions and immutability.

### Functional programming in Java

We go through each of the functional programming rules and see if Java can adhere to it. For this part we made the
assumption we are talking about no version prior to Java 8, and we'll be using the latest version (Java 16) ourselves,
since it provides (hopefully) more features. Moreover, I provide the examples below inside the `Main` class, purely
because of IntelliJ's formatting.

We take a look at first-class and higher-order functions. Traditionally it was only possible to pass functions in Java
using constructs like functional interfaces or anonymous inner classes. However, in Java 8 and above brought us lambda
expressions, method references and predefined functional interfaces.

Using Single Abstract Method (SAM) interfaces:

```java
class Main {
    public static void main(String[] args) {
        Collections.sort(numbers, new Comparator<Integer>() {
            @Override
            public int compare(Integer n1, Integer n2) {
                return n1.compareTo(n2);
            }
        });
    }
}
```

Using the more modern lambda expression can help us do the same task, but in a more concise and understandable manner:

```java
class Main {
    public static void main(String[] args) {
        Collections.sort(numbers, (n1, n2) -> n1.compareTo(n2));
    }
}
```

Note that this is not a first-class citizen function in Java. Java still wraps these into functional interfaces and
treats a lambda expression as an `Object`, which is the first-class citizen in Java.

Since, Java is at its core an object-oriented language it recommends encapsulation of methods in a class, and therefore
these methods are not strictly pure functions. These are however, recommendations and not binding. We can indeed sum all
the numbers we sorted in the examples above, in a deterministic manner and without producing side effects.

```java
import java.util.stream.Collectors;

class Main {
    Integer sum(List<Integer> numbers) {
        return numbers.stream().collect(Collectors.summingInt(Integer::intValue));
    }
}
```

Next, we have immutability, which unlike in a functional programming language is not supported at Java's core. It is
however, possible to create immutable data structures. An example of this is Java's `String`. In general, if we want to
achieve immutability, we use the `final` keyword. We can go about creating our own data structure using this keyword.

```java
public class Person {
    private final String name;
    private final int age;

    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

This way it's quite difficult to achieve full immutability, since all fields must be immutable (this also applies to
nested types and collections), and there should only be accessor methods (without side effects). There are external
libraries which can make this easier such as *Immutables* and *Project Lombok*. Furthermore, as per Java 15, we can
transform the above example into a much compacter, less boilerplate version using the new record class.

```java
public record Person (String name, int age) {}
```

This does the exact same as the previous example.

We now take a look at the `Function` interface in Java. We can create two functions `abs` and `sqrt` like this:

```java
class Main {
    Function<Double, Double> abs = x -> x < 0 ? -x : x;
    Function<Double, Double> sqrt = Math::sqrt;
}
```

We can even use function composition to create the composite of `abs` and `sqrt` like this:

```java
class Main {
    Function<Double, Double> absThenSqrt = sqrt.compose(abs);
}
```

Java also has the `BiFunction`, which takes two arguments. In this way we can implement `float` division:

```java
class Main {
    BiFunction<Float, Float, Float> divide = (x, y) -> x / y;
}
```

For functions with more arguments, we can create our own interface. Let us take a three-argument function.

```java
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
```

Next, we take a look at currying in Java. It's not as straightforward as in pure functional programming languages:

```java
class Main {
    Function<Double, Function<Double, Double>> weight = mass -> gravity -> mass * gravity;

    Function<Double, Double> weightOnEarth = weight.apply(9.81);
}
```

We have defined a function to calculate the weight on any given planet, given the gravity constant. We can partially
apply the function byh passing just the gravity, then later passing the weight. Note that this function is not executed
until we call `weightOnEarth.apply(80.0)`.

Just like any language we can apply recursion. We take the classical Fibonacci sequence as example.

```java
class Main {
    Integer fibonacci(Integer n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
```

### N-Queens in a functional manner

Our code can be found [here](https://github.com/jellehuibregtse/n-queens).

## Conclusion

All in all, clearly Java is more suitable for object-oriented programming. Since, Java 8, strides have been made to make
Java more functional programming friendly, but fundamentally it will probably never be. Since, there are no true
function types in Java and types are inherently mutable. Java benefits most from a combination of object-oriented
programming with some functional programming.

## Sources

- https://www.baeldung.com/cs/functional-programming
- https://www.baeldung.com/java-functional-programming
- https://www.baeldung.com/java-15-new