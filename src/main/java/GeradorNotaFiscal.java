import java.util.List;

public class GeradorNotaFiscal {

    public class GeradorDeNotaFiscal {

        private List<AcaoAposGerarNota> acoes;

        public GeradorDeNotaFiscal(List<AcaoAposGerarNota> acoes) {
            this.acoes = acoes;

        }

        public NotaFiscal gera(Fatura fatura) {

            double valor = fatura.getValorMensal();

            NotaFiscal nf = new NotaFiscal(valor, impostoSimplesSobreO(valor));

            for (AcaoAposGerarNota acaoAposGerarNota : acoes) {
                acaoAposGerarNota.executa(nf);
            }

            return nf;
        }

        private double impostoSimplesSobreO(double valor) {
            return valor * 0.06;
        }
    }

    public class NotaFiscal {

        private int id;
        private double valorBruto;
        private double impostos;

        public NotaFiscal(int id, double valorBruto, double impostos) {
            this.id = id;
            this.valorBruto = valorBruto;
            this.impostos = impostos;
        }

        public NotaFiscal(double valorBruto, double impostos) {
            this(0, valorBruto, impostos);
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public double getValorBruto() {
            return valorBruto;
        }
        public void setValorBruto(double valorBruto) {
            this.valorBruto = valorBruto;
        }
        public double getImpostos() {
            return impostos;
        }
        public void setImpostos(double impostos) {
            this.impostos = impostos;
        }

        public double getValorLiquido() {
            return this.valorBruto - this.impostos;
        }

    }

    public class Fatura {

        private double valorMensal;
        private String cliente;

        public Fatura() {}

        public Fatura(double valorMensal, String cliente) {
            this.valorMensal = valorMensal;
            this.cliente = cliente;
        }
        public double getValorMensal() {
            return valorMensal;
        }
        public void setValorMensal(double valorMensal) {
            this.valorMensal = valorMensal;
        }
        public String getCliente() {
            return cliente;
        }
        public void setCliente(String cliente) {
            this.cliente = cliente;
        }


    }

    public interface AcaoAposGerarNota {
        void executa(NotaFiscal nf);
    }

    public class EnviadorDeEmail implements AcaoAposGerarNota {

        @Override
        public void executa(NotaFiscal nf) {
            System.out.println("envia email da nf " + nf.getId());
        }
    }

    public class NotaFiscalDao implements AcaoAposGerarNota {

        @Override
        public void executa(NotaFiscal nf) {
            System.out.println("salva nf no banco");
        }
    }


}