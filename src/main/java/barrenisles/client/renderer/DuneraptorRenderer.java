package barrenisles.client.renderer;

import barrenisles.client.model.DuneraptorModel;
import barrenisles.common.entity.DuneraptorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DuneraptorRenderer extends GeoEntityRenderer<DuneraptorEntity>{
	
	public DuneraptorRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new DuneraptorModel());
		this.shadowRadius = 0.7F;
	}
}