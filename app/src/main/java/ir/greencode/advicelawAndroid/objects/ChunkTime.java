package ir.greencode.advicelawAndroid.objects;

import java.util.ArrayList;

public class ChunkTime {
    ArrayList<FreeTime> freeTimes;

    public ChunkTime(ArrayList<FreeTime> freeTimes) {
        this.freeTimes = freeTimes;
    }

    public ArrayList<FreeTime> getFreeTimes() {
        return freeTimes;
    }
    public boolean isValid(){
        if(freeTimes.size()==1){
            return true;
        }else {
            boolean valid = true;
            for(int i = 0 ; i<freeTimes.size()-1;i++){
                if(freeTimes.get(i).getBlockId()+1!=freeTimes.get(i+1).getBlockId()){
                    valid = false;
                    break;
                }
            }
            return valid;
        }
    }
}
