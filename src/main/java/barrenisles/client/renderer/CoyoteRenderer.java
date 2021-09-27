package barrenisles.client.renderer;

import barrenisles.client.model.CoyoteModel;
import barrenisles.common.entity.CoyoteEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CoyoteRenderer extends GeoEntityRenderer<CoyoteEntity>{
	
	public CoyoteRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new CoyoteModel());
		this.shadowRadius = 0.7F;
	}
}