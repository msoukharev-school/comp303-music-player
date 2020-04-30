public abstract class AbstractController implements Controller {

    private Device aPairedDevice;

    public AbstractController (Device pDevice) {
        aPairedDevice = pDevice;
    }

    public void next() {
        aPairedDevice.next();
    }

}
