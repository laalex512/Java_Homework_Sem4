package Sem4.Homework;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Tasks {

  public static void main(String[] args) {
    task4Func();
  }
/////////////////////////////////////////////////////////////////////////////////////////////////
  public static void task1Func() {
    Logger logger = Logger.getAnonymousLogger();
    FileHandler fh;
    try {
      fh = new FileHandler(
          "D:\\OneDrive\\Работа\\GeekBrains\\1 четверть\\Java\\Sem4\\Homework\\log.txt");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
    } catch (Exception e) {
      System.out.println("Error");
    }

    LinkedList initList = new LinkedList<>();
    final int COUNT_NUMBERS = 10;
    Random random = new Random();
    for (int i = 0; i < COUNT_NUMBERS; i++) {
      initList.add(random.nextInt(100));
    }
    logger.info(initList.toString());
    initList = reverseList(initList);
    logger.info(initList.toString());
  }

  public static LinkedList reverseList(LinkedList someList) {
    LinkedList resultList = new LinkedList<>();
    for (int i = someList.size() - 1; i >= 0; i--) {
      resultList.add(someList.get(i));
    }
    return resultList;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  public static void task2Func() {
    Logger logger = Logger.getAnonymousLogger();
    FileHandler fh;
    try {
      fh = new FileHandler(
          "D:\\OneDrive\\Работа\\GeekBrains\\1 четверть\\Java\\Sem4\\Homework\\log.txt");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
    } catch (Exception e) {
      System.out.println("Error");
    }

    Queue<String> initQueue = new LinkedList();

    while (true) {
      logger.info(initQueue.toString());
      Scanner iScan = new Scanner(System.in);
      System.out.printf("Insert message (q - to Escape): ");
      String currentMessage = iScan.next();
      if (currentMessage.equals("deq")) {
        String tempElement = "First element: " + dequeue(initQueue);
        logger.info(tempElement);
      } else if (currentMessage.equals("first")) {
        String tempElement = "First element: " + first(initQueue);
        logger.info(tempElement);
      } else if (currentMessage.equals("q")) {
        break;
      } else {
        enqueue(initQueue, currentMessage);
      }
    }
  }

  public static void enqueue(Queue someList, String message) {
    someList.offer(message);
  }

  public static String dequeue(Queue<String> someList) {
    return someList.poll();
  }

  public static String first(Queue<String> someList) {
    return someList.peek();
  }
//////////////////////////////////////////////////////////////////////////////////////////
  public static void task4Func() {
    Logger logger = Logger.getAnonymousLogger();
    FileHandler fh;
    try {
      fh = new FileHandler(
          "D:\\OneDrive\\Работа\\GeekBrains\\1 четверть\\Java\\Sem4\\Homework\\log.txt");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
    } catch (Exception e) {
      System.out.println("Error");
    }

    String initPolynom = "-50+3 * 5+50-3*(5+2)";
    ArrayList<String> polynomArray = polynomToArray(initPolynom);
    logger.info(polynomArray.toString());
    Deque<String> resultPolynom = arrayToPostfix(polynomArray);
    logger.info(resultPolynom.toString());
    int result = calculatingPostfix(resultPolynom);
    logger.info(String.valueOf(result));
  }

  public static ArrayList polynomToArray(String initPolynom) {
    ArrayList resultArray = new ArrayList();
    initPolynom = initPolynom.replace(" ", "");
    String currentElement = "";
    for (int i = 0; i < initPolynom.length(); i++) {
      if (isANumber(String.valueOf(initPolynom.charAt(i)))) {
        currentElement += initPolynom.charAt(i);
      } else {
        if (currentElement != ""){
          resultArray.add(currentElement);
        }
        currentElement = "";
        resultArray.add(String.valueOf(initPolynom.charAt(i)));
      }
      if (i == initPolynom.length() - 1 && !currentElement.equals("")) {
        resultArray.add(currentElement);
      }
    }
    return resultArray;
  }

  public static boolean isANumber(String symbol) {
    try {
      Integer.parseInt(symbol);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static Deque arrayToPostfix(ArrayList<String> initList){
    HashMap<String, Integer> priorityOperation = new HashMap<>();
    priorityOperation.put("(", 0);
    priorityOperation.put("*", 0);
    priorityOperation.put("/", 0);
    priorityOperation.put("+", 1);
    priorityOperation.put("-", 1);

    Deque<String> tempStack = new LinkedList<>();
    Deque<String> resultPolynom = new LinkedList<>();

    for (String i : initList) {
      if (isANumber(i)) {
        resultPolynom.add(i);
      } else if (i.equals("(")) {
        tempStack.add(i);
      } else if (i.equals(")")) {
        while (tempStack.size() > 0) {
          if (!tempStack.getLast().equals("(")) {
            resultPolynom.add(tempStack.pollLast());
          } else {
            tempStack.pollLast();
            break;
          }
        }
      } else {
        if (
            tempStack.size() == 0 ||
                tempStack.getLast().equals("(") ||
                priorityOperation.get(i) < priorityOperation.get(tempStack.getLast())
        ) {
          tempStack.addLast(i);
        } else if (priorityOperation.get(i) >= priorityOperation.get(tempStack.getLast())) {
          while (tempStack.size() >= 0) {
            if (
                tempStack.size() == 0 ||
                    tempStack.getLast().equals("(") ||
                    priorityOperation.get(i) < priorityOperation.get(tempStack.getLast())
            ) {
              tempStack.addLast(i);
              break;
            } else {
              resultPolynom.add(tempStack.pollLast());
            }
          }
        }
      }
    }
    if (tempStack.size()>0){
      while (tempStack.size()>0){
        resultPolynom.add(tempStack.pollLast());
      }
    }
    return resultPolynom;
  }

  public static int calculatingPostfix(Deque<String> initPostfix){
    Deque<Integer> result = new LinkedList<>();
    while (initPostfix.size()>0) {
      String currentElement = initPostfix.pollFirst();
      if (isANumber(currentElement)){
        result.add(Integer.parseInt(currentElement));
      }
      else {
        if (result.size()<2 && currentElement.equals("-")){
          result.addLast(-result.pollFirst());
        }
        else{
          int a;
          switch (currentElement){
            case("+"):
              result.addLast(result.pollLast()+result.pollLast());
              break;
            case("-"):
              a = result.pollLast();
              result.addLast(result.pollLast()-a);
              break;
            case("*"):
              result.addLast(result.pollLast()*result.pollLast());
              break;
            case("/"):
              a = result.pollLast();
              result.addLast(result.pollLast()/a);
              break;
          }
        }
      }
    }
    return result.pollLast();
  }
}
