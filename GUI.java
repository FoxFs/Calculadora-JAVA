import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* tela */

public class GUI extends JFrame {
    private JTextField campoTexto; //AQUI VAI MOSTRAR OS OBJETOS

    public GUI(){
        //CONFIG DO LAYOUT
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());

        campoTexto = new JTextField();
        campoTexto.setEditable(false); 
        painel.add(campoTexto, BorderLayout.NORTH);

        // Define o tamanho do campo de texto
        campoTexto.setPreferredSize(new Dimension(100, 100)); // Define a largura como 300 pixels e a altura como 50 pixels

        JPanel Btpainel = new JPanel();
        Btpainel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] bt = {
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".",
        };

        for (String TextoBt : bt){
            JButton botao = new JButton(TextoBt);
            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //RECEBE O BOTAO QUE VC CLICOU
                    String texto = ((JButton) e.getSource()).getText();
                    
                    //LANÇA PRO CAMPO DE TEXTO O BOTAO CLICADO
                    campoTexto.setText(campoTexto.getText() + texto);
                }
            });

            Btpainel.add(botao);
        }

        //ADICIONAR BOTÃO A TELA
        painel.add(Btpainel, BorderLayout.CENTER);

        // Adiciona o botão "=" em um painel separado
        JPanel painelBotaoIgual = new JPanel();
        JButton botaoIgual = new JButton("=");
        botaoIgual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String expressao = campoTexto.getText();

                try{
                    double resultado = eval(expressao);
                    campoTexto.setText(Double.toString(resultado));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao calcular o resultado.");
                }
            }
        });

        painelBotaoIgual.add(botaoIgual);

        // Adiciona o painel com o botão "=" ao painel principal na região SUL (SOUTH)
        painel.add(painelBotaoIgual, BorderLayout.SOUTH);


        //ADD O PAINEL COM AS INFORMACÕES
        getContentPane().add(painel);

        //GUI
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

   // Método para avaliar uma expressão matemática simples (sem parênteses)
    
    private double eval(String expressao) {
    System.out.println("Expressão: " + expressao); // Depuração: Imprime a expressão
    String[] partes = expressao.split(" ");
    double resultado = Double.parseDouble(partes[0]);
    System.out.println("Primeiro número: " + resultado); // Depuração: Imprime o primeiro número
    for (int i = 1; i < partes.length; i += 2) {
        String operador = partes[i];
        double numero = Double.parseDouble(partes[i + 1]);
        System.out.println("Operador: " + operador); // Depuração: Imprime o operador
        System.out.println("Próximo número: " + numero); // Depuração: Imprime o próximo número
        switch (operador) {
            case "+":
                resultado += numero;
                break;
            case "-":
                resultado -= numero;
                break;
            case "*":
                resultado *= numero;
                break;
            case "/":
                if (numero != 0) { // Verifica divisão por zero
                    resultado /= numero;
                } else {
                    throw new ArithmeticException("Divisão por zero!"); // Lança exceção
                }
                break;
        }
        System.out.println("Resultado parcial: " + resultado); // Depuração: Imprime o resultado parcial
    }
    return resultado;
}



    /* base do codigo */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GUI());
    }
}