package navigation;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public abstract class RegionDecorator extends Region{
    protected Region region;

    protected RegionDecorator(Region region) {
        super(region.description);
        this.region = region;
    }
}
