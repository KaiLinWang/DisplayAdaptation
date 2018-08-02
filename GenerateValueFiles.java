import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by Mars on 18/07/19.
 */
public class GenerateValueFiles {

	private int designW;
	private int designH;
	private int baseDP;

	private String dirStr = "./res/values";

	private final static String WTemplate = "<dimen name=\"x{0}\">{1}dp</dimen>\n";
	private final static String HTemplate = "<dimen name=\"y{0}\">{1}dp</dimen>\n";

	public GenerateValueFiles(int designW, int designH, int baseDP) {
		this.designW = designW;
		this.designH = designH;
		this.baseDP = baseDP;

		File dir = new File(dirStr);
		if (!dir.exists()) {
			dir.mkdir();
		}

	}

	public void generate() {
		generateXmlFile(designW, designH, baseDP);

	}

	private void generateXmlFile(int designW, int designH, int baseDP) {
		float baseX = 1.0f * baseDP / designW;
		float baseY = 1.0f * designH * baseDP / designW / designH;

		// x
		StringBuffer sbForWidth = new StringBuffer();
		sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForWidth.append("<resources>\n");
		for (int i = 1; i < designW; i++) {
			sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
					change(baseX * i) + ""));
		}
		sbForWidth.append(WTemplate.replace("{0}", designW + "").replace("{1}",
				baseDP + ""));
		sbForWidth.append("</resources>");

		// y
		StringBuffer sbForHeight = new StringBuffer();
		sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForHeight.append("<resources>\n");
		for (int i = 1; i < designH; i++) {
			sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}",
					change(baseY * i) + ""));
		}
		sbForHeight.append(HTemplate.replace("{0}", designH + "").replace(
				"{1}", change(1.0f * designH * baseDP / designW) + ""));
		sbForHeight.append("</resources>");

		File layxFile = new File(new File(dirStr).getAbsolutePath(),
				"dimens_x.xml");
		File layyFile = new File(new File(dirStr).getAbsolutePath(),
				"dimens_y.xml");
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
			pw.print(sbForWidth.toString());
			pw.close();
			pw = new PrintWriter(new FileOutputStream(layyFile));
			pw.print(sbForHeight.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static float change(float a) {
		int temp = (int) (a * 100);
		return temp / 100f;
	}

	public static void main(String[] args) {
		// 此处填写设计图上的最大宽度px
		int designW = 750;
		// 此处填写设计图上的最大高度px
		int designH = 1334;
		// 输出dp的最大宽值
		int baseDP = 360;

		new GenerateValueFiles(designW, designH, baseDP).generate();
	}

}