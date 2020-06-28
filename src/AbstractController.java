/**
 * Class AbstractController represents a controller object.
 * It is paired with a single device.
 */
public abstract class AbstractController implements Controller {

    /**
     * Variable contains a Device object that is paired with the controller.
     * A device can have multiple controllers.
     * A controller is paired to one device only.
     */
    private Device aPairedDevice;

    public AbstractController (Device pDevice) {
        aPairedDevice = pDevice;
    }

    @Override
    public void next() {
        aPairedDevice.next();
    }
}
