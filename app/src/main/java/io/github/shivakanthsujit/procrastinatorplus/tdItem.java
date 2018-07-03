package io.github.shivakanthsujit.procrastinatorplus;

public class tdItem {
    String head, sub;
    int done;

    public tdItem()
    {
        head = "";
        sub = "";
        done = 0;
    }
    public tdItem(String H) {
        head = H;
        done = 0;
    }

    public tdItem(String H, String hd){
        head = H;
        sub = hd;
        done = 0;
    }

    public int retS()
    {
        return done;
    }

    public void changeD(char c,String H)
    {
        if(c=='H')
            head = H;
        else if(c=='s')
            sub = H;
    }
    public void done()
    {
        done = 1;
    }

}
