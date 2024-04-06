package mintychochip.mintychochip.horsepoop.container;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import org.bukkit.ChatColor;

public class MendelianGene { //could just do diploids
    private final MendelianAllele alleleA;

    private final MendelianAllele alleleB;
    public MendelianGene(MendelianAllele alleleA, MendelianAllele alleleB) {
        this.alleleA = alleleA;
        this.alleleB = alleleB;
    }
    public MendelianAllele getAlleleA() {
        return alleleA;
    }

    public MendelianAllele getAlleleB() {
        return alleleB;
    }

    @Override
    public String toString() {
        return this.alleleA + " " + this.alleleB;
    }

    public MendelianGene cross(MendelianGene gene) {
        Random random = new Random(System.currentTimeMillis());
        MendelianAllele a = random.nextBoolean() ? this.alleleA : gene.alleleA;
        MendelianAllele b = random.nextBoolean() ? this.alleleB : gene.alleleB;
        return new MendelianGene(a,b);
    }
    private String colorCodeAlleles(MendelianAllele allele) {
        ChatColor color = allele == MendelianAllele.RECESSIVE ? ChatColor.GREEN: ChatColor.RED;
        return color + allele.toString();
    }
    public MendelianType getPhenotype() {
        return alleleA == MendelianAllele.RECESSIVE && alleleB == MendelianAllele.RECESSIVE ? MendelianType.MENDELIAN_RECESSIVE : MendelianType.MENDELIAN_DOMINANT;
    }

    public MendelianType getGenotype() {
        if(this.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
            return MendelianType.MENDELIAN_RECESSIVE;
        }
        return alleleA == MendelianAllele.DOMINANT && alleleB == MendelianAllele.DOMINANT ? MendelianType.MENDELIAN_DOMINANT : MendelianType.MENDELIAN_HETEROZYGOUS;
    }

}