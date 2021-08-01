package edu.kit.kastel.scbs.pcm2java4joana.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.gson.Gson;

public class SupplierAnalysisModel {
	private Resource sourceCodeModel;
	private CorrespondenceModel correspondenceModel;
	private IPath destinationFolder;

	public SupplierAnalysisModel(Resource sourceCodeModel, CorrespondenceModel correspondenceModel,
			IPath destinationFolder) {
		this.sourceCodeModel = sourceCodeModel;
		this.correspondenceModel = correspondenceModel;
		this.destinationFolder = destinationFolder;
	}

	public Resource getSourceCodeModel() {
		return this.sourceCodeModel;
	}

	public void saveSourceCodeModel() {
		try {
			this.sourceCodeModel.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveCorrespondenceModel() {
		String correspondenceModelPath = this.destinationFolder.toString() + IPath.SEPARATOR + "GeneratedModels"
				+ IPath.SEPARATOR + "correspondenceModel.json";
		File file = new File(correspondenceModelPath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Gson gson = new Gson();
		Type type;
		String json = gson.toJson(this.correspondenceModel);
		try {
			PrintWriter writer = new PrintWriter(correspondenceModelPath, "UTF-8");
			writer.print(json);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
