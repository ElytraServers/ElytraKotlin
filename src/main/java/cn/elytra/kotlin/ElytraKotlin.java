package cn.elytra.kotlin;

import kotlin.KotlinVersion;

import javax.swing.*;

public final class ElytraKotlin {

	public static void main(String[] args) {
		try {
			Class.forName("kotlin.KotlinVersion");
			JOptionPane.showMessageDialog(null, "Kotlin "+KotlinVersion.CURRENT+" Inside");
		} catch(ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Kotlin Not Found!!", "Elytra Kotlin", JOptionPane.ERROR_MESSAGE);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Elytra Kotlin", JOptionPane.ERROR_MESSAGE);
		}
	}

}
