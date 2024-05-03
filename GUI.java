import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
            "0", ".", "/",
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
                    double resultado = calcularExpressao(expressao);
                    campoTexto.setText(Double.toString(resultado));
                    

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao calcular o resultado.");
                }
            }
            
            public static double calcularExpressao(String expressao) {
                //remover espaço vazio
                expressao = expressao.replaceAll("\\s+", "");
                
                Stack<Double> numeros = new Stack<>();
                Stack<Character> operadores = new Stack<>();
                
                for (int i = 0; i < expressao.length(); i++) {
                    char c = expressao.charAt(i);
                    if (Character.isDigit(c)) {
                        //extrair numero da expressao
                        StringBuilder numero = new StringBuilder();
                        numero.append(c);
                        while (i + 1 < expressao.length() && Character.isDigit(expressao.charAt(i + 1))) {
                            numero.append(expressao.charAt(++i));
                        }
                        numeros.push(Double.parseDouble(numero.toString()));
                    } else if (isOperador(c)) {
                        //jogar o operador pro grupo dos operadores
                        while (!operadores.isEmpty() && precedencia(operadores.peek()) >= precedencia(c)) {
                            calcularOperacao(numeros, operadores);
                        }
                        operadores.push(c);
                    }
                }
                
                while (!operadores.isEmpty()) {
                    calcularOperacao(numeros, operadores);
                }
                
                return numeros.pop();
            }
            
            private static boolean isOperador(char c) {
                return c == '+' || c == '-' || c == 'x' || c == '/';
            }
            
            private static int precedencia(char operador) {
                if (operador == 'x' || operador == '/') {
                    return 2;
                } else if (operador == '+' || operador == '-') {
                    return 1;
                } else {
                    return 0;
                }
            }
            
            private static void calcularOperacao(Stack<Double> numeros, Stack<Character> operadores) {
                double b = numeros.pop();
                double a = numeros.pop();
                char operador = operadores.pop();
                switch (operador) {
                    case '+':
                        numeros.push(a + b);
                        break;
                    case '-':
                        numeros.push(a - b);
                        break;
                    case 'x':
                        numeros.push(a * b);
                        break;
                    case '/':
                        numeros.push(a / b);
                        break;
                }
            }
        });

        painelBotaoIgual.add(botaoIgual);

        //adiciona o painel com o botão "=" ao painel principal na região SUL (SOUTH)
        painel.add(painelBotaoIgual, BorderLayout.AFTER_LAST_LINE);

        //adiciona botao delete na caculadora
        JPanel PainelBotaoDeletar = new JPanel();
        JButton BotaoDeletar = new JButton("Del");
        BotaoDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                campoTexto.setText("");
            }
        });

        //adiciona botao ao painel
        PainelBotaoDeletar.add(BotaoDeletar);

        painel.add(PainelBotaoDeletar, BorderLayout.AFTER_LINE_ENDS);

        //ADD O PAINEL COM AS INFORMACÕES
        getContentPane().add(painel);

        //GUI
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    // base do codigo
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GUI());
    }
}

