package pdf.reportediario.parqueadero;
import java.sql.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import interfaces.parqueadero.*;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

public class ReporteDiarioEspecifico {
    private String fecha;
    
    public ReporteDiarioEspecifico(String fecha){
        this.fecha = fecha;
        System.out.println("**"+this.fecha);
    }
    
    public void GenerarReporteEspecifico(){
        parqueaderoRegistro pr = new parqueaderoRegistro();
        //String nombrePdf = pr.label_fecha.getText();
        //System.out.println(fechaHoy);
        Rectangle pageSize = new Rectangle(900f,500f);
        Document documento = new Document(pageSize);
        
        
        //
        //String ruta = System.getProperty("user.home");
        //System.out.println(ruta + "\\Desktop\\tiquetes\\reportes\\"+nombrePdf+".pdf");
        try{
            
        String ruta = System.getProperty("user.home");
        PdfWriter.getInstance(documento, new FileOutputStream(ruta + "\\Desktop\\tiquetes\\reportesGenerados\\"+"reporte-"+fecha+".pdf"));
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
                PreparedStatement pst = cn.prepareStatement("select * from park where Fecha like '%"+fecha+"%'");
                //PreparedStatement pst = cn.prepareStatement("select * from parq where Fecha = "+pr.label_fecha.getText());
                
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    //
                    
                    do{
                    //esta condicion es para que haga los reportes diarios y en los pdfs
                    //no acumule los del dia anterior etc.
                    //if(rs.getString(6).equals(fechaHoy)){   
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
                    //}
                    }while(rs.next());
                    documento.add(tabla);
                    JOptionPane.showMessageDialog(null,"Reporte creado.");
                    //
                }
            }catch(DocumentException | SQLException e){
                System.out.println("Error e conexion " + e);
            }
            documento.close();
            //reporte creado
        }catch(DocumentException | FileNotFoundException e){
            System.out.println("Error en PDF " + e);
        }
    }
}