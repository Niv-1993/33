package Presentation;

import BusinessLayer.Fcade.StorageService;

public class CLIPresentation {
    StorageService SS;
    public CLIPresentation(){
        SS=new StorageService();
    }

    public void start() {
        System.out.print("Welcome to SuperLi storage management.\n");
    }
}
