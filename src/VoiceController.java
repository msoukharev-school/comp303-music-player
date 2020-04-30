public class VoiceController extends AbstractController {

    public VoiceController(Device pDevice) {
        super(pDevice);
    }

    @Override
    public void next() {
        System.out.print("Voice command <next> received. ");
        super.next();
    }
}
