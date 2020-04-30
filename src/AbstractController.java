public abstract class AbstractController implements Controller {

    private Device aPairedDevice;

    public AbstractController (Device pDevice) {
        aPairedDevice = pDevice;
    }

    /**
     * invokes next() method of the paired device
     */
    public void next() {
        aPairedDevice.next();
    }

}
