
package bomberman;

/**
 *
 * @author David Benes
 */
class Fire extends MapObject {
    
    private int alarm;

    public Fire(int posX, int posY) {
        super(posX, posY);
        this.alarm = 0;
    }

    public int getAlarm() {
        return alarm;
    }
    
    public void alarmClock() {
        this.alarm++;
    }
}
