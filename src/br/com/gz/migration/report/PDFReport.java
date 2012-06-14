package br.com.gz.migration.report;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.ImageIcon;

import br.com.gz.migration.EnSoftware;
import br.com.gz.migration.MigrationDataType;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFReport {

	public void generateReport(MigrationReportData report) {

		Document document = new Document(PageSize.A4);
		Font defFont = new Font(FontFamily.HELVETICA, 16, Font.ITALIC);

		try {

			// criando o documento
			PdfWriter pw = PdfWriter.getInstance(document,
					new FileOutputStream(report.getPath()));
			document.open();
			// -----------------------------------------------

			// adicionando a imagem do cabeçalho
			java.awt.Image im = new ImageIcon((URL) getClass().getResource(
					"/img/report/header.gif")).getImage();
			com.itextpdf.text.Image im2 = com.itextpdf.text.Image.getInstance(
					pw, im, 1.0f);
			im2.scalePercent(51);
			document.add(im2);
			// ----------------------------------------------------

			// adicionando o título
			Paragraph title = new Paragraph(
					"\nRelatório de migração de banco de dados", new Font(
							FontFamily.HELVETICA, 22, Font.BOLDITALIC));
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			// ----------------------------------------------------

			// adicionando o nome do cliente
			Paragraph nomeCliente = new Paragraph("\nCliente: "
					+ report.getNomeCliente(), defFont);
			nomeCliente.setAlignment(Element.ALIGN_LEFT);
			document.add(nomeCliente);
			// -----------------------------

			// adicionando a data da migração
			Calendar c = report.getData();
			Paragraph subTitle = new Paragraph("Data: "
					+ c.get(Calendar.DAY_OF_MONTH) + "/"
					+ +c.get(Calendar.MONTH) + "/" + +c.get(Calendar.YEAR)
					+ " " + +c.get(Calendar.HOUR_OF_DAY) + ":"
					+ +c.get(Calendar.MINUTE), defFont);
			subTitle.setAlignment(Element.ALIGN_LEFT);
			document.add(subTitle);
			// ------------------------

			// adicionando o tipo de migração: anexação ou sobreposição
			Paragraph tipoMigracao = new Paragraph(
					report.isAppend() ? "Modo: Anexar aos registros existentes"
							: "Modo: Sobrepor os registros existentes", defFont);
			tipoMigracao.setAlignment(Element.ALIGN_LEFT);
			document.add(tipoMigracao);
			// --------------------------------------------------------

			// adicionando tabela
			PdfPTable table = new PdfPTable(4); // 3 = colunas

			String strSoftware = "Inválido";

			if (report.getSoftwareFrom() == EnSoftware.SUPERUS) {

				strSoftware = "Superus";

			} else if (report.getSoftwareFrom() == EnSoftware.Teste) {

				strSoftware = "Teste";

			} else if (report.getSoftwareFrom() == EnSoftware.VERSATHO) {

				strSoftware = "Versatho";

			} else if (report.getSoftwareFrom() == EnSoftware.AES) {

				strSoftware = "AES";

			} else if (report.getSoftwareFrom() == EnSoftware.MRS) {

				strSoftware = "MRS";

			} else if (report.getSoftwareFrom() == EnSoftware.OTHER) {

				strSoftware = "Outro";

			}

			Paragraph pSisAtu = new Paragraph(strSoftware, new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clSisAtu = new PdfPCell(pSisAtu);
			clSisAtu.setHorizontalAlignment(Element.ALIGN_CENTER);
			clSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);
			clSisAtu.setColspan(2);

			strSoftware = "Inválido";

			if (report.getSoftwareTo() == EnSoftware.MERCOFLEX) {

				strSoftware = "MercoFlex";

			} else if (report.getSoftwareTo() == EnSoftware.MERCATTO) {

				strSoftware = "Mercatto";

			}

			Paragraph pRet = new Paragraph(strSoftware, new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clRet = new PdfPCell(pRet);
			clRet.setHorizontalAlignment(Element.ALIGN_CENTER);
			clRet.setVerticalAlignment(Element.ALIGN_CENTER);
			clRet.setColspan(2);

			Paragraph pTipoSisAtu = new Paragraph("Tipo de banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pTipoSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clTipoSisAtu = new PdfPCell(pTipoSisAtu);
			clTipoSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clTipoSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pIPSisAtu = new Paragraph("IP do servidor", new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pIPSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clIPSisAtu = new PdfPCell(pIPSisAtu);
			clIPSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clIPSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pPortaSisAtu = new Paragraph("Porta", new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pPortaSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clPortaSisAtu = new PdfPCell(pPortaSisAtu);
			clPortaSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clPortaSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pNomeSisAtu = new Paragraph("Nome do banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pNomeSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clNomeSisAtu = new PdfPCell(pNomeSisAtu);
			clNomeSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clNomeSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pUsuarioSisAtu = new Paragraph(
					"Usuário do banco de dados", new Font(FontFamily.HELVETICA,
							12, Font.BOLD));
			pUsuarioSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clUsuarioSisAtu = new PdfPCell(pUsuarioSisAtu);
			clUsuarioSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clUsuarioSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pSenhaSisAtu = new Paragraph("Senha do banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pSenhaSisAtu.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clSenhaSisAtu = new PdfPCell(pSenhaSisAtu);
			clSenhaSisAtu.setHorizontalAlignment(Element.ALIGN_LEFT);
			clSenhaSisAtu.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pTipoRet = new Paragraph("Tipo de banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pTipoRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clTipoRet = new PdfPCell(pTipoRet);
			clTipoRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clTipoRet.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pIPRet = new Paragraph("IP do servidor", new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pIPRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clIPRet = new PdfPCell(pIPRet);
			clIPRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clIPRet.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pPortaRet = new Paragraph("Porta", new Font(
					FontFamily.HELVETICA, 12, Font.BOLD));
			pPortaRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clPortaRet = new PdfPCell(pPortaRet);
			clPortaRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clPortaRet.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pNomeRet = new Paragraph("Nome do banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pNomeRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clNomeRet = new PdfPCell(pNomeRet);
			clNomeRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clNomeRet.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pUsuarioRet = new Paragraph("Usuário do banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pUsuarioRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clUsuarioRet = new PdfPCell(pUsuarioRet);
			clUsuarioRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clUsuarioRet.setVerticalAlignment(Element.ALIGN_CENTER);

			Paragraph pSenhaRet = new Paragraph("Senha do banco de dados",
					new Font(FontFamily.HELVETICA, 12, Font.BOLD));
			pSenhaRet.setAlignment(Element.ALIGN_CENTER);
			PdfPCell clSenhaRet = new PdfPCell(pSenhaRet);
			clSenhaRet.setHorizontalAlignment(Element.ALIGN_LEFT);
			clSenhaRet.setVerticalAlignment(Element.ALIGN_CENTER);

			table.addCell(clSisAtu);
			table.addCell(clRet);

			table.addCell(clTipoSisAtu);

			switch (report.getDbTypeFrom()) {
			case Oracle:

				table.addCell("Oracle");

				break;

			case MySQL:

				table.addCell("MySQL");

				break;

			case MSSQL:

				table.addCell("SQL Server");

				break;

			case Firebird:

				table.addCell("Firebird");

				break;

			case PostgreeSQL:

				table.addCell("PostgreSQL");

				break;

			default:

				table.addCell("Desconhecido");

				break;

			}

			table.addCell(clTipoRet);

			switch (report.getDbTypeTo()) {
			case Oracle:

				table.addCell("Oracle");

				break;

			case MySQL:

				table.addCell("MySQL");

				break;

			case MSSQL:

				table.addCell("SQL Server");

				break;

			default:

				table.addCell("Desconhecido");

				break;

			}

			table.addCell(clIPSisAtu);
			table.addCell(report.getIpAddressFrom());
			table.addCell(clIPRet);
			table.addCell(report.getIpAddressTo());

			table.addCell(clPortaSisAtu);
			table.addCell("" + report.getPortFrom());
			table.addCell(clPortaRet);
			table.addCell("" + report.getPortTo());

			table.addCell(clNomeSisAtu);
			table.addCell(report.getDbNameFrom());
			table.addCell(clNomeRet);
			table.addCell(report.getDbNameTo());

			table.addCell(clUsuarioSisAtu);
			table.addCell(report.getUsernameFrom());
			table.addCell(clUsuarioRet);
			table.addCell(report.getUsernameTo());

			table.addCell(clSenhaSisAtu);
			table.addCell(report.getPasswordFrom());
			table.addCell(clSenhaRet);
			table.addCell(report.getPasswordTo());

			Paragraph pTable = new Paragraph(
					"\nDescrição dos bancos de dados envolvidos:\n\n", defFont);
			document.add(pTable);
			document.add(table);
			// ------------------

			// tipos de dados migrados
			Paragraph pTipoMigr = new Paragraph("\nDados migrados:\n", defFont);
			List list = new List(false, false);
			list.setListSymbol("                -   ");

			Iterator<MigrationDataType> it = report.getDataTypes().iterator();

			while (it.hasNext()) {

				MigrationDataType dt = it.next();

				switch (dt.getType()) {
				case PRODUTO:

					list.add(new ListItem("Produtos"));

					break;

				case CLIENTE:

					list.add(new ListItem("Clientes"));

					break;

				case FORNECEDOR:

					list.add(new ListItem("Fornecedores"));

					break;

				case DEPARTAMENTO:

					list.add(new ListItem("Departamentos"));

					break;

				case GRUPO:

					list.add(new ListItem("Grupos"));

					break;

				case MARCA:

					list.add(new ListItem("Marcas"));

					break;

				case SETOR:

					list.add(new ListItem("Setores"));

					break;

				case NFENTRADA:

					list.add(new ListItem("Notas fiscais de entrada"));

					break;

				case NFSAIDA:

					list.add(new ListItem("Notas fiscais de saída"));

					break;

				default:

					break;

				}

			}

			document.add(pTipoMigr);
			document.add(list);
			document.add(new Paragraph("\n"));
			// -------------------------

			// qtde de dados migrados
			it = report.getDataTypes().iterator();

			Paragraph par;

			while (it.hasNext()) {

				MigrationDataType dt = it.next();

				switch (dt.getType()) {
				case PRODUTO:

					par = new Paragraph(
							(!report.isAppend()) ? "Total aproximado de produtos migrados: "
									+ dt.getTotal()
									: "Total aproximado de produtos adicionados: "
											+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case CLIENTE:

					par = new Paragraph(
							"Total aproximado de clientes migrados: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case FORNECEDOR:

					par = new Paragraph(
							"Total aproximado de fornecedores migrados: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case DEPARTAMENTO:

					par = new Paragraph(
							"Total aproximado de departamentos migrados: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case GRUPO:

					par = new Paragraph("Total aproximado de grupos migrados: "
							+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case MARCA:

					par = new Paragraph("Total aproximado de marcas migradas: "
							+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case SETOR:

					par = new Paragraph(
							"Total aproximado de setores migrados: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case NFENTRADA:

					par = new Paragraph(
							"Total aproximado de notas fiscais de entrada migradas: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				case NFSAIDA:

					par = new Paragraph(
							"Total aproximado de notas fiscais de saída migradas: "
									+ dt.getTotal(), defFont);
					document.add(par);

					break;

				default:

					break;

				}

			}
			// --------------------------

			document.close();

			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.open(new File(report.getPath()));

		} catch (IOException de) {
			de.printStackTrace();
			document.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			document.close();
		}

	}

}
