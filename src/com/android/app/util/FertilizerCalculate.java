package com.android.app.util;

public class FertilizerCalculate {
	private double var_N[] = { 0.025, 0.02, 0.00375, 0.135 };
	private double var_P[] = { 0.015, 0.02, 0.0025, 0.045 };
	private double var_K[] = { 0.025, 0.01, 0.006, 0.05 };

	/**
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param measuered
	 *            Ԫ�زⶨֵ
	 * @param e_type
	 *            Ԫ�����ͣ�0��ʾN,1��ʾP,2��ʾK
	 * @param z_type
	 *            �������� 0С�� 1���� 2���
	 * @return
	 */
	public double calculateFertillizer(double target, double measuered,
			int e_type, int z_type) {
		double FRelement = 0;
		switch (z_type) {
		case 0: // С��
			switch (e_type) {
			case 0:
				FRelement = var_N[z_type] * target + (10 - measuered) * 0.2 + 3;
				break;
			case 1:
				FRelement = var_P[z_type] * target + (20 - measuered) * 0.1 + 1;
				break;
			case 2:
				FRelement = var_K[z_type] * (target - 300) + (110 - measuered)
						* 0.05 + 1;
				break;
			}
			break; 
		case 1: // ����
			switch (e_type) {
			case 0:
				FRelement = var_N[z_type] * target + (10 - measuered) * 0.25 + 2;
				break;
			case 1:
				FRelement = var_P[z_type] * (target - 400) + (20 - measuered) * 0.15 + 1;
				break;
			case 2:
				FRelement = var_K[z_type] * target + (110 - measuered)* 0.025 - 0.25;
				break;
			}
			break;
		}
	
		return FRelement;
	}

	public static void main(String args[]) {
	/*	FertilizerCalculate fc = new FertilizerCalculate();
		System.out.println(fc.calculateFertillizer(500, 31.3, 0, 0));// ��������500ǧ��С�������N����
		System.out.println(fc.calculateFertillizer(500, 101.3, 1, 0));// ��������500ǧ��С�������N����
		System.out.println(fc.calculateFertillizer(500, 22.4, 2, 0));// ��������500ǧ��С�������N����
*/
	}
}
