package searchengine;

import java.util.*;
import java.lang.Math;

public class VectorSpaceModel {

    /* <File name, <Term, Frequency>> */
    private HashMap<String, HashMap<String, Integer>> tfHashMap = new HashMap<>();

    public VectorSpaceModel() {
    }

    /*
     *
     *
     */
    private double termFrequency(int rawTF) {
        if (rawTF == 0){
            return 0;
        }

        return 1 + Math.log(rawTF);
    }

    /*
     *
     *
     */
    private double inverseDocumentFrequency(String term) {

        return 0;
    }

    /*
     *
     *
     */
    private double[] getVector(String document) {

        return null;
    }

    /*
     * Assigns a score to a document based on a query
     *
     * @param a: Document to score
     * @param b: Query
     */

    public void giveDocumentScore(Document document, Document query) {

        Set<String> uniqueterms = new HashSet<>();
        uniqueterms.addAll(document.getTfidf().keySet());
        uniqueterms.addAll(query.getTfidf().keySet());

        List<Double> documentVector = new ArrayList<>();
        List<Double> queryVector = new ArrayList<>();

        for(String term : uniqueterms){
            documentVector.add(document.getTfidf().getOrDefault(term, 0.0));
            queryVector.add(query.getTfidf().getOrDefault(term, 0.0));
        }

        document.setScore(cosineSimilarityScore(documentVector, queryVector));
    }

    /*
     * Calculates the cosine similarity score between two given vectors
     *
     * @param a: First vector
     * @param b: Second vector
     * @return: The cosine similarity score
     */

    public double cosineSimilarityScore(List<Double> a, List<Double> b) {

        /* The dimension of the vector */
        int n = a.size();

        /* The nominator of the formula for cosine similarity */
        double sum = 0;

        /* The denominator of the formula for cosine similarity */
        double squaredA = 0;
        double squaredB = 0;

        for (int i = 1; i < n; i++) {
            squaredA += Math.pow(a.get(i), 2);
            squaredB += Math.pow(b.get(i), 2);

            sum += a.get(i) * b.get(i);
        }

        return sum / (Math.sqrt(squaredA) * Math.sqrt(squaredB));
    }

    /*
     *
     *
     */
    public void retrieve(String query) {

    }

    private List<Document> createTestData(int documetns){

        List<Document> ret = new ArrayList<Document>();
        int i = 0;

        ret.add(new Document(String.valueOf(i++), "[3H]Adenine has previously been used to label the newly discovered G protein-coupled murine adenine receptors. Recent reports have questioned the suitability of [3H]adenine for adenine receptor binding studies because of curious results, e.g. high specific binding even in the absence of mammalian protein. In this study, we showed that specific [3H]adenine binding to various mammalian membrane preparations increased linearly with protein concentration. Furthermore, we found that Tris-buffer solutions typically used for radioligand binding studies (50ๅสmM, pH 7.4) that have not been freshly prepared but stored at 4ๅกC for some time may contain bacterial contaminations that exhibit high affinity binding for [3H]adenine. Specific binding is abolished by heating the contaminated buffer or filtering it through 0.2-๋_m filters. Three different, aerobic, gram-negative bacteria were isolated from a contaminated buffer solution and identified as Achromobacter xylosoxidans, A. denitrificans, and Acinetobacter lwoffii. A. xylosoxidans, a common bacterium that can cause nosocomial infections, showed a particularly high affinity for [3H]adenine in the low nanomolar range. Structure activity relationships revealed that hypoxanthine also bound with high affinity to A. xylosoxidans, whereas other nucleobases (uracil, xanthine) and nucleosides (adenosine, uridine) did not. The nature of the labeled site in bacteria is not known, but preliminary results indicate that it may be a high-affinity purine transporter. We conclude that [3H]adenine is a well-suitable radioligand for adenine receptor binding studies but that bacterial contamination of the employed buffer solutions must be avoided."));
        ret.add(new Document(String.valueOf(i++), "1,10-Phenanthroline-platinum(II)-ethylenediamine ( PEPt ) forms a crystalline complex with cytidine-3'-phosphate (3'-CMP) and its structure has been determined by X-ray crystallography. 3'-CMP molecules are hemiprotonated and form hydrogen-bonded pairs that stack above and below the phenanthroline-platinum(II) drug molecule. Sugar residues are in the C2' endo conformation, with glycosidic torsional angles intermediate between the high and low anti forms. The structure is of particular interest since it forms as an end product of the hydrolytic cleavage of the dinucleoside monophosphate, CpG, by the platinum organometallointercalator ( PEPt ). This hydrolytic activity appears to be specific for the RNA dinucleoside monophosphate fragment, since deoxycytidylyl (3'-5')deoxyguanosine (d-CpG) and other deoxyribooligonucleotides are not cleaved under similar conditions"));
        ret.add(new Document(String.valueOf(i++), "1,3-Butadiene is a major monomer in the rubber and plastics industry and is one of the highest-production industrial chemicals in the United States. Although not highly acutely toxic to rodents, inhalation of concentrations as low as 6.25 ppm causes tumors in mice. Butadiene is oncogenic in rats, but much higher exposure concentrations are required than in mice. Chronic toxicity targets the gonads and hematopoietic system. Butadiene is also a potent mutagen and clastogen. Differences in the absorption, distribution, and elimination of butadiene appear to be relatively minor between rats and mice, although mice do retain more butadiene and its metabolites after exposure to the same concentration and have a higher rate of metabolic elimination. Recent studies have demonstrated that major species differences appear to occur in the rate of detoxication of the primary metabolite, 3-epoxybutene (butadiene monoepoxide [BDMO]). Mice have the greatest rate of production of BDMO as compared to other species, but the rate of removal of BDMO appears to be less than in other species. Mice have low levels of epoxide hydrolase; rats have intermediate levels; monkeys and humans appear to have high levels of this detoxifying enzyme. Thus, while only low levels of butadiene exposure may result in an accumulation of BDMO in the mouse, much higher levels would be required to result in an elevation of circulating BDMO in other species. The level of this reactive metabolite may be correlated with the species differences in butadiene sensitivity."));
        ret.add(new Document(String.valueOf(i++), "13C T1's and NOE's have been measured for all protonated carbons of 2'-deoxy-D-ribose (2'-d-ribose), 2'-deoxyadenosine-5'-monophosphate (5'-dAMP), thymidine-3'-monophosphate (3'-TMP) and thymidine-5'-monophosphate (5'-TMP) in D2O solutions. In all of the deoxy sugars examined, NT1 values for C-2' are significantly larger than the values for the remaining carbons. This result is interpreted in terms of rapid puckering motion of C-2'. By contrast, NT1 values measured in ribose are found to be equal, within experimental error. The results are compared with analogous data obtained for the five membered pyrrolidine ring of proline and with results for DNA itself."));
        ret.add(new Document(String.valueOf(i++), "2',5'-Linked oligo-3'-deoxyribonucleotides bind selectively to complementary RNA but not to DNA. These oligonucleotides (ODNs) do not recognize double-stranded DNA by Hoogsteen triplex formation and the complexes formed by these ODNs with RNA are not substrates for Escherichia coli RNase H. Substitution of the 2',5'-phosphodiester backbone by phosphorothioate linkages gives 2',5'-linked oligo-3'-deoxynucleoside phosphorothioate ODNs that exhibit significantly less non-specific binding to cellular proteins or thrombin. Incorporation of a stretch of seven contiguous 3',5'-linked oligo-2'-deoxynucleoside phosphorothioate linkages in the center of 2',5'-linked ODNs (as a putative RNase H recognition site) afford chimeric antisense ODNs that retain the ability to inhibit steroid 5alpha-reductase (5alphaR) expression in cell culture."));
        ret.add(new Document(String.valueOf(i++), "2-Deoxyribonolactone (dL) is a C1-oxidized abasic site damage generated by a radical attack on DNA. Numerous genotoxic agents have been shown to produce dL including UV and ๋_-irradiation, ene-dye antibiotics etc. At present the biological consequences of dL present in DNA have been poorly documented, mainly due to the lack of method for introducing the lesion in oligonucleotides. We have recently designed a synthesis of dL which allowed investigation of the mutagenicity of dL in Escherichia coli by using a genetic reversion assay. The lesion was site-specifically incorporated in a double-stranded bacteriophage vector M13G*1, which detects single-base-pair substitutions at position 141 of the lacZ๋ฑ gene by a change in plaque color. In E.coli JM105 the dL-induced reversion frequency was 4.7 105, similar to that of the classic abasic site 2_-deoxyribose (dR). Here we report that a dL residue in a duplex DNA codes mainly for thymidine. The processing of dL in vivo was investigated by measuring lesion-induced mutation frequencies in DNA repair deficient E.coli strains. We showed a 32-fold increase in dL-induced reversion rate in AP endonuclease deficient (xth nfo) mutant compared with wild-type strain, indicating that the Xth and Nfo AP endonucleases participate in dL repair in vivo."));
        ret.add(new Document(String.valueOf(i++), "2-Arylhydrazono-3-oxobutanenitriles 2 was reacted with hydroxylamine hydrochloride to yield amidooxime 3. This was cyclized into the corresponding oxadiazole 4 on refluxing in acetic anhydride. When refluxed in DMF in presence of piperidine, the corresponding 1,2,3-triazoleamine 5 was formed. The latter was acylated to 6 by addition of acetic anhydride while treatment of 5 with malononitrile gave the 1,2,3-triazolo [4,5-b]pyridine 8. Treatment of acetyl derivative 6 with DMFDMA gave enaminone 9. The enaminone 9 was coupled with benzenediazonium chloride to yield phenylazo-1,2,3-triazolo [4,5-b]pyridine 10. Trials to convert compound 14 into 1,2,3-triazolo [4,5-d]pyrimidine 15 via refluxing in AcOH/NH4OAc failed. Instead the hydrolyzed product 5 was formed."));
        ret.add(new Document(String.valueOf(i++), "2'-O-Methyl-3'-O-phosphoramidite building blocks of 6-oxocytidine 6 and its 5-methyl derivative 7, respectively, were synthesized and incorporated via phosphoramidite chemistry in 15 mer oligodeoxynucleotides [d(T72T7), S2; d(T73T7), S3] to obtain potential Py.Pu.Py triplex forming homopyrimidine strands. UV thermal denaturation studies and CD spectroscopy of 1:1 mixtures of these oligomers and a 21 mer target duplex [d(C3A7GA7C3)-d(G3T7CT7G3), D1] with a complementary purine tract showed a nearly pH-independent (6.0-8.0) triple helix formation with melting temperatures of 21-19 degrees C and 18.5-17.5 degrees C, respectively (buffer system: 50 mM sodium cacodylate, 100 mM NaCl, 20 mM MgCl2). In contrast, with the corresponding 15mer deoxy-C-containing oligonucleotide [d(T(7)1T7), S1] triplex formation was observed only below pH 6.6. Specificity for the recognition of Watson-Crick GC-base pairs was observed by pairing the modified C-bases of the 15mers with all other possible Watson-Crick-base compositions in the target duplex [d(C3A7XA7C3)-d(G3T7YT7G3), X = A,C,T; Y = T,G,A, D2-4]. Additionally, the Watson-Crick-pairing of the modified oligomers S2 and S3 was studied."));
        ret.add(new Document(String.valueOf(i++), "2S albumin storage proteins are becoming of increasing interest in nutritional and clinical studies as they have been reported as major food allergens in seeds of many mono- and di-cotyledonous plants. This review describes the main biochemical, structural and functional properties of these proteins thought to play a role in determining their potential allergenicity. 2S albumins are considered to sensitize directly via the gastrointestinal tract (GIT). The high stability of their intrinsic protein structure, dominated by a well-conserved skeleton of cysteine residues, to the harsh conditions present in the GIT suggests that these proteins are able to cross the gut mucosal barrier to sensitize the mucosal immune system and/or elicit an allergic response. The flexible and solvent-exposed hypervariable region of these proteins is immunodominant and has the ability to bind IgE from allergic patientsๅซ sera. Several linear IgE-binding epitopes of 2S albumins spanning this region have been described to play a major role in allergenicity; the role of conformational epitopes of these proteins in food allergy is far from being understood and need to be investigated. Finally, the interaction of these proteins with other components of the food matrix might influence the absorption rates of immunologically reactive 2S albumins but also in their immune response."));
        ret.add(new Document(String.valueOf(i++), "3,N(4)-Ethano-2'-deoxycytidine (ethano-dC) may be incorporated successfully into synthetic oligodeoxynucleotides by omitting the capping procedure used in the automated DNA synthetic protocols immediately after inserting the lesion and in all iterations thereafter. Ethano-dC is sensitive to acetic anhydride found in the capping reagent, and multiple oligomeric products are formed. These products were identified by examining the reaction of ethano-dC with the capping reagent, and several acetylated, ring-opened products were characterized by electrospray mass spectrometry and collision induced dissociation experiments on a tandem quadrupole mass spectrometer. A scheme for the formation of the acetylated products is proposed. In addition, the mutagenic profile of ethano-dC was re-examined and compared to that for etheno-dC. Ethano-dC is principally a blocking lesion; however, when encountered by the exo(-)Klenow fragment of DNA polymerase, dAMP (22%), TMP (16%), dGMP (5.3%) and dCMP (1.2%) were all incorporated opposite ethano-dC, along with an oligomer containing a one-base deletion (0.6%)."));

        return ret;
    }

    private void createTfidf(List<Document> dList){

        HashMap<String, Integer> df = new HashMap<>();
        HashMap<String, Double> idf = new HashMap<>();

        for(Document d : dList){
            for(String term : d.getTermFrequency().keySet()) {
                if (df.containsKey(term)) {
                    df.put(term, df.get(term) + 1);
                }
                else{
                    df.put(term, 1);
                }
            }
        }

        for(String term : df.keySet()) {
            idf.put(term, Math.log(dList.size() / (double) df.get(term)));
        }

        for(Document d : dList) {
            d.createTfidf(idf);
        }
    }
}
