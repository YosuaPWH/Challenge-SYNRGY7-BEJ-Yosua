PGDMP     &                    |            binarfud    14.4    14.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16451    binarfud    DATABASE     h   CREATE DATABASE binarfud WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_Indonesia.1252';
    DROP DATABASE binarfud;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            �            1259    16474    merchant    TABLE     �   CREATE TABLE public.merchant (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    location character varying(255) NOT NULL,
    open boolean DEFAULT false
);
    DROP TABLE public.merchant;
       public         heap    postgres    false    3            �            1259    16511    order_detail    TABLE     �   CREATE TABLE public.order_detail (
    id uuid NOT NULL,
    order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    quantity integer NOT NULL,
    total_price numeric NOT NULL
);
     DROP TABLE public.order_detail;
       public         heap    postgres    false    3            �            1259    16501    orders    TABLE     �   CREATE TABLE public.orders (
    id uuid NOT NULL,
    order_time timestamp without time zone NOT NULL,
    destination_address character varying(255) NOT NULL,
    user_id uuid NOT NULL
);
    DROP TABLE public.orders;
       public         heap    postgres    false    3            �            1259    16489    product    TABLE     �   CREATE TABLE public.product (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    price numeric NOT NULL,
    merchant_id uuid NOT NULL
);
    DROP TABLE public.product;
       public         heap    postgres    false    3            �            1259    16467    users    TABLE     �   CREATE TABLE public.users (
    id uuid NOT NULL,
    username character varying(255) NOT NULL,
    email_address character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false    3                      0    16474    merchant 
   TABLE DATA           <   COPY public.merchant (id, name, location, open) FROM stdin;
    public          postgres    false    210   �       	          0    16511    order_detail 
   TABLE DATA           W   COPY public.order_detail (id, order_id, product_id, quantity, total_price) FROM stdin;
    public          postgres    false    213   �                 0    16501    orders 
   TABLE DATA           N   COPY public.orders (id, order_time, destination_address, user_id) FROM stdin;
    public          postgres    false    212   �                 0    16489    product 
   TABLE DATA           ?   COPY public.product (id, name, price, merchant_id) FROM stdin;
    public          postgres    false    211   �                 0    16467    users 
   TABLE DATA           F   COPY public.users (id, username, email_address, password) FROM stdin;
    public          postgres    false    209          o           2606    16481    merchant merchant_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.merchant DROP CONSTRAINT merchant_pkey;
       public            postgres    false    210            u           2606    16517    order_detail order_detail_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT order_detail_pkey;
       public            postgres    false    213            s           2606    16505    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    212            q           2606    16495    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    211            m           2606    16473    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    209            x           2606    16518 '   order_detail order_detail_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);
 Q   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT order_detail_order_id_fkey;
       public          postgres    false    213    212    3187            y           2606    16523 )   order_detail order_detail_product_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);
 S   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT order_detail_product_id_fkey;
       public          postgres    false    213    211    3185            w           2606    16506    orders orders_user_id_fkey    FK CONSTRAINT     y   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 D   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_user_id_fkey;
       public          postgres    false    209    212    3181            v           2606    16496     product product_merchant_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_merchant_id_fkey FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);
 J   ALTER TABLE ONLY public.product DROP CONSTRAINT product_merchant_id_fkey;
       public          postgres    false    211    210    3183                  x������ � �      	      x������ � �            x������ � �            x������ � �            x������ � �     