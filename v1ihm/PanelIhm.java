import java.awt.event.*;
import javax.swing.*;

public class PanelIhm extends JPanel implements ActionListener
{
	private JTextField idMachine;
	private JTextField numMachine;
	private JTextField numSalle;
	private JTextField nomFichier;
	private JButton envoyer;

	public PanelIhm()
	{
		this.idMachine = new JTextField(10);
		this.numSalle = new JTextField(3);
		this.numMachine = new JTextField(2);
		this.nomFichier = new JTextField(25);
		this.envoyer = new JButton("envoyer");

		this.idMachine.addActionListener(this);
		this.numMachine.addActionListener(this);
		this.numSalle.addActionListener(this);
		this.nomFichier.addActionListener(this);
		this.envoyer.addActionListener(this);

		this.add(this.nomFichier);
		this.add(this.idMachine);
		this.add(this.numMachine);
		this.add(this.numSalle);
		this.add(this.envoyer);
	}
	public void actionPerformed​(ActionEvent e)
	{
		if (e.getSource().equals(this.idMachine))
		{
			if (this.idMachine.getText().substring(0,4).equals("ulh-") &&
				this.idMachine.getText().charAt(7) == '-')
			{
				String texte = this.idMachine.getText();
				this.numSalle.setText(texte.substring(4,7));
				this.numMachine.setText(texte.substring(8));
			}
		}
		else
		{
			if (this.numSalle.getText().length()==3 &&
			    this.numMachine.getText().length()==2)
			{
				this.idMachine.setText("ulh-"+this.numSalle.getText()+
				                       "-"+this.numMachine.getText());
			}
			if (e.getSource().equals(this.envoyer)&&
			    this.idMachine.getText().length()==10 &&
			    !this.nomFichier.getText().equals(""))
			{
				new ClasseTest(this.nomFichier.getText(), this.idMachine.getText(), 8080);
			}

		}
	}
}