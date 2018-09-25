package pdf.reportediario.parqueadero;
import java.sql.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;


import java.io.FileOutputStream;
import javax.swing.JOptionPane;

public class ReportesAnuales {
    String año;    
    
    String mesNumero;
    public ReportesAnuales(String año){
        this.año = año;
       
    }
    
    public void GenerarReporteAnual(){
        Rectangle pageSize = new Rectangle(900f,500f);
        Document documento = new Document(pageSize);
        try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "\\Desktop\\tiquetes\\reportesGenerados\\reporte-Año-"+año+".pdf")); // debe ser unico
            documento.open();
            
            PdfPTable tabla = new PdfPTable(10);
            
           tabla.addCell("No. PARQUEO");
           tabla.addCell("PLACA");
           tabla.addCell("TIPO DE VEHICULO");
           tabla.addCell("MODELO");
           tabla.addCell("COLOR");
           //FECHA
           tabla.addCell("FECHA");
           tabla.addCell("HORA ENTRADA");
           tabla.addCell("HORA SALIDA");
           tabla.addCell("Horas:Min.");
           tabla.addCell("VALOR A PAGAR");
           
           
           try{
               Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/parking","root","");
               //PreparedStatement pst = cn.prepareStatement("select * from park where ");
               PreparedStatement pst = cn.prepareStatement("select * from park where Fecha like '%"+año+"%'");
               ResultSet rs = pst.executeQuery();
               
               if(rs.next()){
                   do{
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                        tabla.addCell(rs.getString(8));
                        tabla.addCell(rs.getString(9));
                        tabla.addCell(rs.getString(10));
                       
                   }while(rs.next());
                   documento.add(tabla);
               }
           }catch(DocumentException | SQLException e){
               e.printStackTrace();
           }
           documento.close();
           JOptionPane.showMessageDialog(null, "Reporte Creado.");
           //
        }catch(DocumentException | HeadlessException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
