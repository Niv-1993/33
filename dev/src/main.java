import org.apache.log4j.Logger;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {

        //CLIPresentation cli= new CLIPresentation();
        //cli.start();

        log.info("hiii");
        log.error("sdaf");
        System.out.println("sdfs");
        log.fatal("asfd");
        f();
    }
    private static void f(){
        log.info("f");
    }
}
