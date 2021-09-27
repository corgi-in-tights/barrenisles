package barrenisles.client.renderer;

import barrenisles.client.model.TumbleweedModel;
import barrenisles.common.entity.TumbleweedEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TumbleweedRenderer extends GeoEntityRenderer<TumbleweedEntity>{
	
	public TumbleweedRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new TumbleweedModel());
		this.shadowRadius = 0.7F;
	}
}