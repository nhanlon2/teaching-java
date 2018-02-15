package uk.gov.ros.teaching.concurrency;

public class DelayWriteUsingVolatile implements Runnable {
  private volatile String str;
  void setStr(String str) {this.str = str;}

  public void run() {
    while (str == null);
    System.out.println(str);
  }
  //If you remove the volatile modifier from the 'str' variable, this program never terminates.
  //The JVM hoists the str variable out of the while loop and optimises it by running
  //while(true) instead.
  public static void main(String[] args) throws InterruptedException {
    DelayWriteUsingVolatile delay = new DelayWriteUsingVolatile();
    new Thread(delay).start();
    Thread.sleep(100);
    delay.setStr("Hello world!!");
  }
}