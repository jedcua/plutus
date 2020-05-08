INSERT INTO store(name, address, tin) VALUES ('Name1', 'Address1', 'Tin1');
INSERT INTO store(name, address, tin) VALUES ('Name2', 'Address2', NULL);
INSERT INTO store(name, address, tin) VALUES ('Name3', 'Address3', 'Tin3');

INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product1', 'Barcode', 123.45, 'pcs', 1);
INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product2',  NULL, 123.45, 'pcs', 1);
INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product3', 'Barcode', 123.45, 'pcs', 1);

DELETE FROM invoice_template;
INSERT INTO invoice_template(id, name, content) VALUES (1, 'Test Template',
'
#set( $lessDiscount = $invoice.total * 0.03 )
#set( $netAmount = $invoice.total - $lessDiscount )
#set( $vatable = $netAmount * 0.89285730589 )
#set( $vatAmount = $netAmount - $vatable )
<html>
<head>
    <style>
        .h-spacer { padding-left: 2.5cm; }
        .line-spacing * {
            line-height: 0.67cm;
        }
        .products * {
            line-height: 0.67cm;
        }
        .product-padding {
            padding-left: 0.2cm;
        }
        .inline-block {
            display: inline-block;
            text-overflow: clip;
            white-space: nowrap;
        }
        .bold { font-weight: bold; }
        .invoice-bg {
            background: url("invoice.png") no-repeat;
            background-size: 22cm 30cm;
        }
    </style>
</head>
<body class="invoice-bg">
<div style="margin-bottom: 4cm"></div>
<div class="h-spacer bold line-spacing">
    <div>
        <span class="inline-block" style="width: 14cm;">$invoice.store.name</span>
        <span>$Template.formatDate($invoice.deliveryDate, "MMMM dd, yyyy")</span>
    </div>
    <div>$invoice.store.address</div>
    <div>
        <span class="inline-block" style="width: 7cm;">RETAIL</span>
        <span>$invoice.store.tin</span>
    </div>
</div>
<div style="margin-bottom: 1.7cm"></div>
<div class="products" style="padding-left: 1cm; font-size: 12px">
    #foreach ($product in $invoice.products)
    <div>
        <span class="inline-block" style="text-align: right; width: 1cm;">$product.quantity</span>
        <span class="inline-block product-padding" style="width: 1cm;">$product.unit</span>
        <span class="inline-block product-padding" style="width: 11.7cm;">$product.name</span>
        <span class="inline-block product-padding" style="text-align: right; width: 1.5cm;">$Template.formatAmount($product.price)</span>
        <span class="inline-block product-padding" style="text-align: right; width: 3cm;">$Template.formatAmount($product.subtotal)</span>
    </div>
    #end
    <div style="margin-bottom: 2.8cm;"></div> <div class="bold" style="padding-left: 11.5cm">
        <div>
            <span class="inline-block" style="width: 3cm;">Total Sales</span>
            <span class="inline-block" style="width: 4.85cm; text-align: right">$Template.formatAmount($invoice.total)</span>
        </div>
        <div>
            <span class="inline-block" style="width: 3cm;">Less Discount 3%</span>
            <span class="inline-block" style="width: 4.85cm; text-align: right">$Template.formatAmount($lessDiscount)</span>
        </div>
        <div>
            <span class="inline-block" style="width: 3cm;">Vatable</span>
            <span class="inline-block" style="width: 4.85cm; text-align: right">$Template.formatAmount($vatable)</span>
        </div>
        <div>
            <span class="inline-block" style="width: 3cm;">Vat Amount</span>
            <span class="inline-block" style="width: 4.85cm; text-align: right">$Template.formatAmount($vatAmount)</span>
        </div>
        <div>
            <span class="inline-block" style="width: 3cm;">Net</span>
            <span class="inline-block" style="width: 4.85cm; text-align: right">$Template.formatAmount($netAmount)</span>
        </div>
    </div>
</div>
</body>
</html>
')
