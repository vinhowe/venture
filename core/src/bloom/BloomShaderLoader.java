package bloom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dc0d.iiridarts.venture.client.Constants;

public final class BloomShaderLoader {

	static final public ShaderProgram createShader(String vertexName,
			String fragmentName) {

		String vertexShader = Gdx.files.internal(
				Constants.SHADERS_DIR + "/" + vertexName
						+ ".vertex.glsl").readString();
		String fragmentShader = Gdx.files.internal(
				Constants.SHADERS_DIR + "/" + fragmentName
						+ ".fragment.glsl").readString();
		ShaderProgram.pedantic = false;
		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.out.println(shader.getLog());
			Gdx.app.exit();
		} else
			Gdx.app.log("shader compiled", shader.getClass().getSimpleName());
		return shader;
	}
}
